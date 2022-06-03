package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.application.exception.CannotUpdateUserNameException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.fixture.PasswordFixture.encryptedBasicPassword;

class CustomerTest {

    @DisplayName("유저 이름을 변경하면 예외가 발생한다.")
    @Test
    void validateUserNameChange() {
        final Customer customer = new Customer(1L, "giron", encryptedBasicPassword);

        assertThatThrownBy(() -> customer.validateUserNameChange("tiki12"))
                .isExactlyInstanceOf(CannotUpdateUserNameException.class)
                .hasMessageContaining("유저 이름을 변경할 수 없습니다.");
    }
}
