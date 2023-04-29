package cart.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.user.UserFieldNotValidException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class UserPasswordTest {

    @Test
    @DisplayName("UserPassword가 생성된다.")
    void createUserPasswordSuccess() {
        UserPassword userPassword = assertDoesNotThrow(() -> new UserPassword("password"));

        assertThat(userPassword).isExactlyInstanceOf(UserPassword.class);
    }

    @ParameterizedTest(name = "password가 {0}이면 예외가 발생한다.")
    @ValueSource(strings = {"", "   "})
    void createUserPasswordFailWithBlankPassword(String password) {
        assertThatThrownBy(() -> new UserPassword(password))
                .isInstanceOf(UserFieldNotValidException.class)
                .hasMessage("비밀번호는 공백을 입력할 수 없습니다.");
    }

    @ParameterizedTest(name = "비밀번호가 password일 때, {0}이 전달되면 {1}을 반환한다.")
    @CsvSource(value = {"password:true", "not-password:false"}, delimiter = ':')
    void matches(String password, boolean expected) {
        UserPassword userPassword = new UserPassword("password");

        assertThat(userPassword.matches(password)).isEqualTo(expected);
    }
}
