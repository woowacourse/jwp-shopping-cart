package cart.repository;

import cart.dto.UserAuthenticationDto;
import cart.exception.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

    @ParameterizedTest
    @CsvSource({"ditoo@wooteco.com, ditoo1234",
            "barrel@wooteco.com, barrel1234",
            "brown@wooteco.com, brown1234",
            "anonymous@wooteco.com, 12345678"})
    @DisplayName("유저 조회 성공")
    void findIdByEmailAndPassword_success(String email, String password) {
        // given, when
        Integer id = userDao.findIdByEmailAndPassword(email, password);

        // then
        assertThat(id).isNotNull();
    }

    @Test
    @DisplayName("잘못된 인증 정보이면 예외가 발생한다.")
    void findIdByEmailAndPassword_fail() {
        assertThatThrownBy(() -> userDao.findIdByEmailAndPassword("pobi", "pobipobi"))
                .isInstanceOf(AuthorizationException.class);
    }
}