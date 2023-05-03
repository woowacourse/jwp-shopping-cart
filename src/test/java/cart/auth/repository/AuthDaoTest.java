package cart.auth.repository;

import cart.exception.AuthorizationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;

import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@JdbcTest
class AuthDaoTest {

    @Autowired
    private DataSource dataSource;

    private AuthDao authDao;

    @BeforeEach
    void setUp() {
        authDao = new AuthDao(dataSource);
    }

    @ParameterizedTest
    @CsvSource({"ditoo@wooteco.com, ditoo1234",
            "barrel@wooteco.com, barrel1234",
            "brown@wooteco.com, brown1234",
            "anonymous@wooteco.com, 12345678"})
    @DisplayName("유저 조회 성공")
    void findIdByEmailAndPassword_success(String email, String password) {
        // given, when
        final Integer id = authDao.findIdByEmailAndPassword(email, password);

        // then
        assertThat(id).isNotNull();
    }

    @Test
    @DisplayName("잘못된 인증 정보이면 예외가 발생한다.")
    void findIdByEmailAndPassword_fail() {
        assertThatThrownBy(() -> authDao.findIdByEmailAndPassword("pobi", "pobipobi"))
                .isInstanceOf(AuthorizationException.class);
    }
}