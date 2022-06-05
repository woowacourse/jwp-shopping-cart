package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

class PasswordTest {

    @DisplayName("객체 생성 시 비밀번호가 규칙에 어긋나는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "",
            " ",
            "asd123!",
            "abcdefghijklmnop1234!",
            "qerqwerqwad!",
            "asdfasdf123",
            "123123123!",
            "alsdifaencwe",
            "12312312313",
            "!!!!!!!!!!!",
            "password 123",
            "password123{"
    })
    void createByInvalidPassword(final String invalidPassword) {
        assertThatThrownBy(() -> new Password(invalidPassword))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("잘못된 비밀번호 형식입니다.");
    }
}
