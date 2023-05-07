package cart.service;

import cart.controller.dto.request.LoginRequest;
import cart.controller.dto.response.UserResponse;
import cart.database.dao.UserDao;
import cart.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthServiceTest {

    private AuthService authService;
    @Mock
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(userDao);
    }

    @DisplayName("로그인 정보 확인")
    @Test
    public void basicLogin() {
        //given
        String email = "test@example.com";
        String password = "password";
        UserEntity userEntity = new UserEntity(1L, email, password);
        LoginRequest request = new LoginRequest(email, password);

        when(userDao.findByEmailAndPassword(email, password)).thenReturn(userEntity);

        UserResponse expectedResponse = new UserResponse(userEntity.getId(), email, password);

        //when
        UserEntity actualEntity = authService.basicLogin(request);

        //then
        assertAll(
                () -> assertEquals(expectedResponse.getId(), actualEntity.getId()),
                () -> assertEquals(expectedResponse.getEmail(), actualEntity.getEmail()),
                () -> assertEquals(expectedResponse.getPassword(), actualEntity.getPassword())
        );

        verify(userDao).findByEmailAndPassword(email, password);
    }
}

