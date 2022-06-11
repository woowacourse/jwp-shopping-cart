package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.format.InvalidPasswordFormatException;
import woowacourse.shoppingcart.support.PasswordEncoder;

class PasswordTest {
    @DisplayName("평문을 전달받아 생성한다.")
    @Test
    void fromPlainText() {
        // given
        String password = "a1@12345";

        // when
        Password actual = Password.fromPlainText(password);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("올바르지 않은 패스워드 포맷을 전달하면 예외가 발생한다.")
    @ValueSource(strings = {"", "12345678", "a@qwerty", "@1ertyu", "asdqwdqew"})
    @ParameterizedTest
    void fromPlainText_invalidFormat(String input) {
        // when & then
        assertThatThrownBy(() -> Password.fromPlainText(input))
                .isInstanceOf(InvalidPasswordFormatException.class);
    }

    @DisplayName("인코딩된 문자열을 입력받아 생성한다.")
    @Test
    void constructor() {
        // given
        String password = "a1!12345";

        // when
        Password actual = new Password(password);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("같은 내용의 인코딩된 텍스트와 평문으로부터 생성된 두 객체를 비교했을 경우 true를 반환한다.")
    @Test
    void matches() {
        // given
        String originalPassword = "a1!12345";
        String cipherTextFromDatabase = PasswordEncoder.encrypt(originalPassword);
        Password passwordFromDatabase = new Password(cipherTextFromDatabase);
        String passwordFromRequest = "a1!12345";

        // when
        boolean actual = passwordFromDatabase.matches(passwordFromRequest);

        // then
        assertThat(actual).isTrue();
    }
}
