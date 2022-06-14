package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.exception.bodyexception.ValidateException;

public class PasswordTest {

    @DisplayName("비밀번호 양식이 잘 못된 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"asdfasdfaf", "123424352", "asdf123"})
    void new_wrongForm_exceptionThrown(String value) {
        // when, then
        assertThatThrownBy(() -> Password.hashPassword(value))
                .isInstanceOf(ValidateException.class)
                .hasMessage("비밀번호 양식이 잘못 되었습니다.");
    }

    @DisplayName("Password를 생성하면, 비밀번호가 암호화 된다.")
    @Test
    void hashPassword() {
        //given
        String value = "qwer123456";

        //when
        Password password = Password.hashPassword(value);
        String actual = password.getValue();

        //then
        Assertions.assertThat(actual).isNotEqualTo(value);
    }
}
