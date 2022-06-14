package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.common.exception.InputFormatException;

public class PasswordTest {

    @ParameterizedTest(name = "{0}은 영어, 숫자, 특수문자가 모두 들어있지 않으므로 에러가 발생한다.")
    @ValueSource(strings = {"5566", "1", "12!", "abc", "a", "a!", "1!", "!!!"})
    void passwordNotContainsAll(String value) {
        assertThatThrownBy(() -> Password.fromPlainInput(value)).isInstanceOf(InputFormatException.class);
    }

    @ParameterizedTest(name = "{0}은 영어, 숫자, 특수문자가 모두 들어있으나 8~12자가 아니므로 에러가 발생한다.")
    @ValueSource(strings = {"5566aa!", "11223344aabb!"})
    void passwordFailByLength(String value) {
        assertThatThrownBy(() -> Password.fromPlainInput(value)).isInstanceOf(InputFormatException.class);
    }

    @DisplayName("같은 문자열로 암호화하였을 경우 두 암호화된 패스워드는 같다.")
    @Test
    void encryptSamePassword() {
        //given
        final Password password1 = Password.fromPlainInput("password0!");
        final Password password2 = Password.fromPlainInput("password0!");

        //then
        assertThat(password1).isEqualTo(password2);
    }

    @DisplayName("다른 문자열로 암호화하였을 경우 두 암호화된 패스워드는 다르다.")
    @Test
    void encryptDifferentPassword() {
        //given
        final Password password1 = Password.fromPlainInput("password0!");
        final Password password2 = Password.fromPlainInput("password1!");

        //then
        assertThat(password1).isNotEqualTo(password2);
    }
}
