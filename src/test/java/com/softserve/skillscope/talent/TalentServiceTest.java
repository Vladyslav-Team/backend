package com.softserve.skillscope.talent;

import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.mapper.talent.TalentMapper;
import com.softserve.skillscope.general.model.ImageResponse;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.proof.model.entity.Proof;
import com.softserve.skillscope.skill.model.dto.SkillWithVerification;
import com.softserve.skillscope.skill.model.entity.Skill;
import com.softserve.skillscope.talent.model.dto.TalentProfile;
import com.softserve.skillscope.talent.model.entity.Talent;
import com.softserve.skillscope.talent.model.request.TalentEditRequest;
import com.softserve.skillscope.talent.service.impl.TalentServiceImpl;
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
class TalentServiceTest {
    @Mock
    private TalentRepository talentRepo;
    @Mock
    private TalentMapper talentMapper;
    @Mock
    private UtilService utilService;
    @InjectMocks
    private TalentServiceImpl talentService;
    private User user;
    private Talent talent;

    @BeforeEach
    public void createTalent() {
        user = User.builder()
                .id(1L)
                .email("vladkharchenko@gmail.com")
                .password("vladkharchenko_123")
                .name("Vlad")
                .surname("Kharchenko")
                .roles(Set.of(Role.TALENT.getAuthority()))
                .build();

        talent = Talent.builder()
                .id(1L)
                .user(user)
                .image("https://static.wikia.nocookie.net/bleach/images/f/f1/" +
                        "Ch686IchikaCharaPic.png/revision/latest?cb=20210912041854&path-prefix=en")
                .experience("Java 2 years")
                .location("Poland")
                .phone("+380937771122")
                .birthday(LocalDate.of(2003, 2, 17))
                .about("Nothing interesting")
                .balance(100)
                .proofs(List.of(new Proof()))
                .skills(Set.of(new Skill()))
                .build();
    }

    @Test
    @DisplayName("Get talent profile")
    @Order(2)
    void getTalentProfile() {
        //talentMapper.toTalentProfile(talent); is pacifier, so you do this
        // TalentProfile talentProfile1 = talentMapper.toTalentProfile(talent);
        TalentProfile talentProfile = TalentProfile.builder()
                .id(1L)
                .name("Vlad")
                .surname("Kharchenko")
                .age(20)
                .image("https://static.wikia.nocookie.net/bleach/images/f/f1/" +
                        "Ch686IchikaCharaPic.png/revision/latest?cb=20210912041854&path-prefix=en")
                .experience("Java 2 years")
                .location("Poland")
                .phone("+380937771122")
                .about("Nothing interesting")
                .balance(100)
                .skills(Set.of(SkillWithVerification.builder().build()))
                .build();
        log.info("talentProfile={}", talentProfile);
        //when -> then (setup for my service), for every mock, every when
        //when receives only mocks
        when(utilService.findTalentById(talent.getId())).thenReturn(talent);
        when(talentMapper.toTalentProfile(talent)).thenReturn(talentProfile);
        // Act (pacifier into service, so we use injection)
        talentService.getTalentProfile(talent.getId());
        // Assert
        assertThat(talentProfile).isNotNull();
    }

    @Test
    @DisplayName("Get talent image")
    @Order(3)
    void getTalentImage() {
        ImageResponse imageResponse = new ImageResponse("https://static.wikia.nocookie.net/bleach/images/f/f1" +
                "/Ch686IchikaCharaPic.png/revision/latest?cb=20210912041854&path-prefix=en");
        when(utilService.findTalentById(talent.getId())).thenReturn(talent);
        when(talentMapper.toTalentImage(talent)).thenReturn(imageResponse);
        talentService.getTalentImage(talent.getId());
        assertThat(imageResponse).isNotNull();
    }

    @Test
    @DisplayName("Check if not Current User")
    @Order(4)
    void given_Talent_WhenIsNotCurrentUser_Then_ThrowForbiddenRequestException() {
        when(utilService.isNotCurrentUser(talent.getUser())).thenThrow(ForbiddenRequestException.class);
        Assertions.assertThrows(ForbiddenRequestException.class, () -> {
            utilService.isNotCurrentUser(talent.getUser());
        });
    }

    @Test
    @DisplayName("Edit talent profile")
    @Order(5)
    void given_editTalent_When_SavedTalent_Then_Response() {
        Talent newTalent = mock(Talent.class);
        Talent saveTalent = mock(Talent.class);
        User mockedUser = mock(User.class);
        TalentEditRequest talentEditRequest = TalentEditRequest.builder().build();

        when(utilService.findTalentById(5L)).thenReturn(newTalent);
        when(newTalent.getUser()).thenReturn(mockedUser);
        //we don't face the exception
        when(utilService.isNotCurrentUser(mockedUser)).thenReturn(false);
        when(talentRepo.save(newTalent)).thenReturn(saveTalent);
        when(saveTalent.getId()).thenReturn(5L);
        talentService.editTalentProfile(5L, talentEditRequest);
        //Check if methods were called
        verify(utilService).findTalentById(5L);
        verify(utilService).isNotCurrentUser(mockedUser);
        verify(talentRepo).save(newTalent);
        verify(saveTalent).getId();
    }
}
