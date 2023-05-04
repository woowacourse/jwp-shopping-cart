package cart.service;

import cart.controller.dto.response.UserResponse;
import cart.database.dao.UserDao;
import cart.entity.UserEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class UserServiceTest {

    private UserService userService;

    @Mock
    private UserDao userDao;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userDao);
    }

    @DisplayName("모든 User 조회 테스트")
    @Test
    public void findAll() {
        //given
        List<UserEntity> userEntities = new ArrayList<>();
        UserEntity userEntity1 = new UserEntity(1L, "user1@test.com", "password1");
        UserEntity userEntity2 = new UserEntity(2L, "user2@test.com", "password2");
        userEntities.add(userEntity1);
        userEntities.add(userEntity2);

        when(userDao.findAll()).thenReturn(userEntities);

        //when
        List<UserResponse> userResponses = userService.findAll();

        //then
        Assertions.assertEquals(userEntities.size(), userResponses.size());

        for (int i = 0; i < userEntities.size(); i++) {
            UserEntity userEntity = userEntities.get(i);
            UserResponse userResponse = userResponses.get(i);

            Assertions.assertEquals(userEntity.getId(), userResponse.getId());
            Assertions.assertEquals(userEntity.getEmail(), userResponse.getEmail());
            Assertions.assertEquals(userEntity.getPassword(), userResponse.getPassword());
        }
    }
}
