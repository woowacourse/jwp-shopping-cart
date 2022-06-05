package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

class EmailTest {

    @DisplayName("객체 생성 시 이메일이 규칙에 어긋나는 경우 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {
            "잘못된 이메일",
            "이메일@email.com",
            "iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii@gmail.com",
            "emailemail.com",
            "email@emailcom",
            "@email.com",
            "email@email",
            " "
    })
    void createByInvalidEmail(final String invalidEmail) {
        assertThatThrownBy(() -> new Email(invalidEmail))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("잘못된 이메일 형식입니다.");
    }
}
