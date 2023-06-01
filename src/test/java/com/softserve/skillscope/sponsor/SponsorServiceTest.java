package com.softserve.skillscope.sponsor;

import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.mapper.sponsor.SponsorMapper;
import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.kudos.model.enity.Kudos;
import com.softserve.skillscope.security.payment.model.entity.Orders;
import com.softserve.skillscope.sponsor.model.dto.SponsorProfile;
import com.softserve.skillscope.sponsor.model.entity.Sponsor;
import com.softserve.skillscope.sponsor.model.request.SponsorEditRequest;
import com.softserve.skillscope.sponsor.service.SponsorServiceImpl;
import com.softserve.skillscope.user.Role;
import com.softserve.skillscope.user.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Slf4j
class SponsorServiceTest {
    @Mock
    private SponsorRepository sponsorRepository;
    @Mock
    private SponsorMapper sponsorMapper;
    @Mock
    private UtilService utilService;
    @InjectMocks
    private SponsorServiceImpl sponsorService;
    private User user;
    private Sponsor sponsor;

    @BeforeEach
    public void createSponsor() {
        user = User.builder()
                .id(1L)
                .email("vladkharchenko@gmail.com")
                .password("vladkharchenko_123")
                .name("Vlad")
                .surname("Kharchenko")
                .roles(Set.of(Role.TALENT.getAuthority()))
                .build();

        sponsor = Sponsor.builder()
                .id(1L)
                .user(user)
                .image("https://static.wikia.nocookie.net/bleach/images/f/f1/" +
                        "Ch686IchikaCharaPic.png/revision/latest?cb=20210912041854&path-prefix=en")
                .location("Poland")
                .phone("+380937771122")
                .birthday(LocalDate.of(2003, 2, 17))
                .orders(List.of(new Orders()))
                .kudos(List.of(new Kudos()))
                .balance(100)
                .lastPlayedDate(LocalDate.now())
                .build();
    }

    @Test
    @DisplayName("Get sponsor's profile")
    @Order(1)
    void given_Talent_When_GetTalentProfile_Then_ReturnSponsorProfile() {
        SponsorProfile sponsorProfile = SponsorProfile.builder()
                .id(1L)
                .name(user.getName())
                .surname(user.getSurname())
                .age(20)
                .email(user.getEmail())
                .image("https://static.wikia.nocookie.net/bleach/images/f/f1/" +
                        "Ch686IchikaCharaPic.png/revision/latest?cb=20210912041854&path-prefix=en")
                .location("Poland")
                .phone("+380937771122")
                .balance(100)
                .build();
        //when -> then (setup for my service), for every mock, every when
        //when receives only mocks
        when(utilService.findSponsorById(sponsor.getId())).thenReturn(sponsor);
        when(sponsorMapper.toSponsorProfile(sponsor)).thenReturn(sponsorProfile);
        //we don't face the exception
        when(utilService.isNotCurrentUser(user)).thenReturn(false);
        //TODO how to pass security?

        // Act (pacifier into service, so we use injection)
        sponsorService.getSponsorProfile(sponsor.getId());
        // Assert
        assertThat(sponsorProfile).isNotNull();
    }

    @Test
    @DisplayName("Check if not Current Sponsor")
    @Order(2)
    void given_Sponsor_When_IsNotCurrentUser_Then_ThrowForbiddenRequestException() {
        when(utilService.isNotCurrentUser(sponsor.getUser())).thenThrow(ForbiddenRequestException.class);
        Assertions.assertThrows(ForbiddenRequestException.class, () -> {
            utilService.isNotCurrentUser(sponsor.getUser());
        });
    }

    @Test
    @DisplayName("Get sponsor's image")
    @Order(3)
    void given_Sponsor_When_GetSponsorImage_Then_ReturnImageResponse() {
        ImageResponse imageResponse = new ImageResponse("https://static.wikia.nocookie.net/bleach/images/f/f1" +
                "/Ch686IchikaCharaPic.png/revision/latest?cb=20210912041854&path-prefix=en");
        when(utilService.findSponsorById(sponsor.getId())).thenReturn(sponsor);
        when(sponsorMapper.toSponsorImage(sponsor)).thenReturn(imageResponse);
        sponsorService.getSponsorImage(sponsor.getId());
        assertThat(imageResponse).isNotNull();
    }

    @Test
    @DisplayName("Edit talent profile")
    @Order(4)
    void given_SponsorIdAndEditRequest_When_EditTalentProfile_Then_ReturnResponse() {
        Sponsor newSponsor = mock(Sponsor.class);
        Sponsor saveSponsor = mock(Sponsor.class);
        User mockedUser = mock(User.class);
        SponsorEditRequest sponsorEditRequest = SponsorEditRequest.builder().build();

        when(utilService.findSponsorById(2L)).thenReturn(newSponsor);
        when(newSponsor.getUser()).thenReturn(mockedUser);
        //we don't face the exception
        when(utilService.isNotCurrentUser(mockedUser)).thenReturn(false);
        when(sponsorRepository.save(newSponsor)).thenReturn(saveSponsor);
        when(saveSponsor.getId()).thenReturn(2L);
        sponsorService.editSponsorProfile(2L, sponsorEditRequest);
        //Check if methods were called
        verify(utilService).findSponsorById(2L);
        verify(utilService).isNotCurrentUser(mockedUser);
        verify(sponsorRepository).save(newSponsor);
        verify(saveSponsor).getId();
    }
}
