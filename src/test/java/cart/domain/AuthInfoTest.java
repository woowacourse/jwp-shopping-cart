package cart.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.SoftAssertions.assertSoftly;

class AuthInfoTest {

    @DisplayName("올바른 이메일과 패스워드가 들어왔을 때 입력된 이메일과 패스워드를 가진다.")
    @Test
    void AuthInfoGenerationTest() {
        String validEmail = "email@email.com";
        String validPassword = "password";

        AuthInfo authInfo = new AuthInfo(validEmail, validPassword);

        assertSoftly(softly -> {
            softly.assertThat(authInfo.getEmail()).isEqualTo(validEmail);
            softly.assertThat(authInfo.getPassword()).isEqualTo(validPassword);
        });
    }

    @DisplayName("형식에 맞지 않는 이메일이 들어왔을 때 예외가 발생한다")
    @Test
    void throwExceptionWhenInvalidEmailTest() {
        String invalidEmail = "email";
        String validPassword = "password";

        Assertions.assertThatThrownBy(() -> new AuthInfo(invalidEmail, validPassword))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("형식에 맞지 않는 패스워드가 들어왔을 때 예외가 발생한다")
    @Test
    void throwExceptionWhenInvalidPasswordTest() {
        String invalidEmail = "email@email.com";
        String validPassword = "password1234567890password1234567890";

        Assertions.assertThatThrownBy(() -> new AuthInfo(invalidEmail, validPassword))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
