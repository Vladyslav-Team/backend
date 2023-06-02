package com.softserve.skillscope.user;

import com.softserve.skillscope.general.handler.exception.generalException.ForbiddenRequestException;
import com.softserve.skillscope.general.util.service.UtilService;
import com.softserve.skillscope.user.model.User;
import com.softserve.skillscope.user.service.impl.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepo;
    @InjectMocks
    private UserServiceImpl userService;
    @Mock
    private UtilService utilService;
    private User user;

    @BeforeEach
    public void createUser() {
        user = User.builder()
                .id(1L)
                .email("vladkharchenko@gmail.com")
                .password("vladkharchenko_123")
                .name("Vlad")
                .surname("Kharchenko")
                .roles(Set.of(Role.TALENT.getAuthority()))
                .build();
    }

    @Test
    @DisplayName("Delete user")
    @Order(2)
    void given_editTalent_When_SavedTalent_Then_Response() {
        User mockedUser = mock(User.class);
        when(userRepo.findById(5L)).thenReturn(Optional.ofNullable(mockedUser));

        //we don't face the exception
        when(utilService.isNotCurrentUser(mockedUser)).thenReturn(false);

        userService.deleteUser(5L);
        //Check if methods were called
        verify(userRepo).findById(5L);
        verify(utilService).isNotCurrentUser(mockedUser);
        verify(userRepo).delete(mockedUser);
    }

    @Test
    @DisplayName("Check if not Current User")
    @Order(3)
    void given_Talent_WhenIsNotCurrentUser_Then_ThrowForbiddenRequestException() {
        when(utilService.isNotCurrentUser(user)).thenThrow(ForbiddenRequestException.class);
        Assertions.assertThrows(ForbiddenRequestException.class, () -> {
            utilService.isNotCurrentUser(user);
        });
    }
}
