package cart.repository;

import cart.entity.UserEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserDaoTest {

    @Autowired
    private DataSource dataSource;

    private UserDao userDao;

    @BeforeEach
    void setUp() {
        userDao = new UserDao(dataSource);
    }

    @Test
    @DisplayName("전체 유저 조회 테스트")
    void findAll() {
        // given, when
        List<UserEntity> allUsers = userDao.findAll();

        // then
        assertAll(
                () -> assertThat(allUsers.size()).isEqualTo(4),
                () -> assertThat(allUsers.get(0).getEmail()).isEqualTo("ditoo@wooteco.com"),
                () -> assertThat(allUsers.get(0).getPassword()).isEqualTo("ditoo1234"),
                () -> assertThat(allUsers.get(0).getName()).isEqualTo("디투"),
                () -> assertThat(allUsers.get(3).getName()).isEqualTo("익명의 사용자")
        );

    }
}