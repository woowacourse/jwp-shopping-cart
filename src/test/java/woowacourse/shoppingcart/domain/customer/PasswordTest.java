package woowacourse.shoppingcart.domain.customer;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.dataempty.CustomerDataEmptyException;
import woowacourse.shoppingcart.exception.dataformat.CustomerDataFormatException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PasswordTest {

    @DisplayName("비밀번호에 null 을 입력하면 안된다.")
    @Test
    void passwordNullException() {
        // when & then
        assertThatThrownBy(() -> new Password(null))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"", " "})
    @DisplayName("비밀번호에 빈값을 입력하면 안된다.")
    void passwordBlankException(String password) {
        // when & then
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(CustomerDataEmptyException.class)
                .hasMessage("비밀번호를 입력해주세요.");
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345678!", "asdsad^f#$", "1231234ads", "asd2$$", "adsfsdaf324234#@$#@$#@"})
    @DisplayName("비밀번호가 영문, 한글, 숫자를 필수로 조합한 8 ~ 16 자가 아니면 안된다.")
    void passwordFormatException(String password) {
        // when & then
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(CustomerDataFormatException.class)
                .hasMessage("비밀번호는 영문, 한글, 숫자를 필수로 조합하여 8 ~ 16 자를 입력해주세요.");
    }
}
