package cart.dao;

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

}
