package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.fixture.PasswordFixture.encryptedBasicPassword;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.CannotUpdateUserNameException;

class CustomerTest {

    @DisplayName("유저 이름을 변경하면 예외가 발생한다.")
    @Test
    void validateUserNameChange() {
        final Customer customer = new Customer(1L, new UserName("giron"), encryptedBasicPassword);

        assertThatThrownBy(() -> customer.validateUserNameChange("티키"))
                .isExactlyInstanceOf(CannotUpdateUserNameException.class)
                .hasMessageContaining("유저 이름을 변경할 수 없습니다.");
    }
}
