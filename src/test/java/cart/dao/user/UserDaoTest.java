package cart.dao.user;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@Import(UserDao.class)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class UserDaoTest {

    @Autowired
    private UserDao userDao;

    @DisplayName("User 저장 테스트")
    @Test
    void createUser() {
        String email = "zuny@naver.com";
        UserEntity userEntity = new UserEntity(null, email, "zuny1234");

        userDao.insert(userEntity);

        List<UserEntity> allUsers = userDao.findAll();
        assertThat(allUsers).hasSize(1);
        assertThat(allUsers.get(0).getEmail())
                .isEqualTo(email);
    }

    @DisplayName("특정 User 조회 테스트")
    @Test
    void findUserById() {
        String email = "zuny@naver.com";
        UserEntity userEntity = new UserEntity(null, email, "zuny1234");

        Long userId = userDao.insert(userEntity);

        UserEntity findedUserEntity = userDao.findById(userId).get();
        assertThat(findedUserEntity.getEmail())
                .isEqualTo(email);
    }

    @DisplayName("User 삭제 테스트")
    @Test
    void deleteUserById() {
        String email = "zuny@naver.com";
        UserEntity userEntity = new UserEntity(null, email, "zuny1234");

        Long savedUserId = userDao.insert(userEntity);
        assertThat(userDao.findAll()).hasSize(1);
        userDao.deleteById(savedUserId);

        assertThat(userDao.findAll()).hasSize(0);
    }

}
