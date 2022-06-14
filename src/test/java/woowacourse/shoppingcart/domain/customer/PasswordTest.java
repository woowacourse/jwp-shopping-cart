package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InputFormatException;

public class PasswordTest {

    @ParameterizedTest
    @DisplayName("패스워드가 8~12글자가 아니라면 에러를 발생한다.")
    @EmptySource
    @ValueSource(strings = {"asdasd", "password0", "password01234"})
    void InputWrongLengthPassword(String value) {
        //then
        assertThatThrownBy(() -> Password.ofWithEncryption(value))
                .isInstanceOf(InputFormatException.class);
    }

    @ParameterizedTest
    @DisplayName("패스워드가 8~12글자이고 알파벳, 숫자, 특수문자의 조합이라면 정상적으로 생성된다.")
    @ValueSource(strings = {"password1!", "Password012="})
    void InputRightPassword(String value) {
        //then
        assertThatCode(() -> Password.ofWithEncryption(value))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @DisplayName("패턴에 맞지 않는 비밀번호라면 에러를 발생한다.")
    @EmptySource
    @ValueSource(strings = {"pwdpwdpwd", "pwdPWD12", "PWDpwd!!!"})
    void InputWrongPatternPassword(String value) {
        //then
        assertThatThrownBy(() -> Password.ofWithEncryption(value))
                .isInstanceOf(InputFormatException.class);
    }

    @DisplayName("같은 문자열로 암호화하였을 경우 두 암호화된 패스워드는 같다.")
    @Test
    void encryptSamePassword() {
        //given
        final Password password1 = Password.ofWithEncryption("password0!");
        final Password password2 = Password.ofWithEncryption("password0!");

        //then
        assertThat(password1).isEqualTo(password2);
    }

    @DisplayName("다른 문자열로 암호화하였을 경우 두 암호화된 패스워드는 다르다.")
    @Test
    void encryptDifferentPassword() {
        //given
        final Password password1 = Password.ofWithEncryption("password0!");
        final Password password2 = Password.ofWithEncryption("password1!");

        //then
        assertThat(password1).isNotEqualTo(password2);
    }
}
