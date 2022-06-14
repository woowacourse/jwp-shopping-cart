package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.exception.InputFormatException;
import woowacourse.shoppingcart.customer.domain.Password;

public class PasswordTest {

    @DisplayName("패스워드는 10글자 이상이어야 한다.")
    @Test
    void password() {
        assertThatThrownBy(()-> Password.from("pwd")).isInstanceOf(InputFormatException.class);
    }

    @DisplayName("같은 문자열로 암호화하였을 경우 두 암호화된 패스워드는 같다.")
    @Test
    void encryptSamePassword() {
        //given
        final Password password1 = Password.from("password0!");
        final Password password2 = Password.from("password0!");

        //then
        assertThat(password1).isEqualTo(password2);
    }

    @DisplayName("다른 문자열로 암호화하였을 경우 두 암호화된 패스워드는 다르다.")
    @Test
    void encryptDifferentPassword() {
        //given
        final Password password1 = Password.from("password0!");
        final Password password2 = Password.from("password1!");

        //then
        assertThat(password1).isNotEqualTo(password2);
    }
}
