package cart.database.dao;

import cart.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
class UserDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private UserDao userDao;
    private String userEmail;
    private String userPassword;

    @BeforeEach
    void setUp() {
        userDao = new UserDao(jdbcTemplate);

        userEmail = "test@test.com";
        userPassword = "password";

        jdbcTemplate.update(
                "INSERT INTO `USER` (EMAIL, PASSWORD) VALUES (?, ?)",
                userEmail, userPassword
        );
    }

    @DisplayName("모든 사용자 가져오기 테스트")
    @Test
    void findAll() {
        List<UserEntity> users = userDao.findAll();

        assertNotNull(users);
        assertTrue(users.size() > 0);
    }

    @DisplayName("이메일과 비밀번호로 사용자 정보를 가져오기 테스트")
    @Test
    void findByEmailAndPassword() {
        UserEntity user = userDao.findByEmailAndPassword(userEmail, userPassword);

        assertNotNull(user);
        assertEquals(userEmail, user.getEmail());
        assertEquals(userPassword, user.getPassword());
    }
}
