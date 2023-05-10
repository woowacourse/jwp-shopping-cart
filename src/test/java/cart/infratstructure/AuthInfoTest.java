package cart.infratstructure;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.exception.AuthInfoFormatException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AuthInfoTest {

    @DisplayName("형식에 맞는 이메일, 비밀번호이면 인증 정보를 생성한다")
    @Test
    void create() {
        assertDoesNotThrow(() -> new AuthInfo("dummy@gmail.com", "abcd1234"));
    }

    @DisplayName("형식에 맞지 않는 이메일로 인증 정보 생성 시 예외를 던진다")
    @ParameterizedTest
    @ValueSource(strings = {"", "a", "@", "abc@"})
    void validateEmail(String email) {
        assertThatThrownBy(() -> new AuthInfo(email, "abcd1234"))
                .isInstanceOf(AuthInfoFormatException.class);
    }

    @DisplayName("형식에 맞지 않는 비밀번호로 인증 정보 생성 시 예외를 던진다")
    @ParameterizedTest
    @ValueSource(strings = {"", "12345678", "abcd123", "abcdefgh"})
    void validatePassword(String password) {
        assertThatThrownBy(() -> new AuthInfo("abc@gmail.com", password))
                .isInstanceOf(AuthInfoFormatException.class);
    }
}
