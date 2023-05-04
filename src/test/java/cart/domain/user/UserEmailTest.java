package cart.domain.user;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class UserEmailTest {

    @ParameterizedTest(name = "이메일 형식이 아닌 경우 예외가 발생한다.")
    @ValueSource(strings = {
            "w.ww@E##@@.com",
            "1231emailcom",
            "@aa123123.com",
    })
    void createUserEmailFailure(String email) {
        assertThatThrownBy(() -> UserEmail.from(email))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest(name = "옳바른 이메일 형식인 경우 생성.")
    @ValueSource(strings = {
            "woowa@gmail.com",
            "k123@gmail.com",
            "wawa@wowo.net",
    })
    void createUserEmail(String email) {
        assertDoesNotThrow(() -> UserEmail.from(email));
    }
}
