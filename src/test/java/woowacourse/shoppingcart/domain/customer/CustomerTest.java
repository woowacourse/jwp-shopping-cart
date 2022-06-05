package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.fixture.PasswordFixture.plainBasicPassword;
import static woowacourse.fixture.PasswordFixture.plainReversePassword;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.CannotUpdateUserNameException;

class CustomerTest {

    @DisplayName("유저 이름을 변경하면 예외가 발생한다.")
    @Test
    void validateUserNameChange() {
        final Customer customer = new Customer(1L, new UserName("giron"), new EncryptPassword(plainBasicPassword));

        assertThatThrownBy(() -> customer.validateUserNameChange("티키"))
                .isExactlyInstanceOf(CannotUpdateUserNameException.class)
                .hasMessageContaining("유저 이름을 변경할 수 없습니다.");
    }

    @DisplayName("비밀 번호가 일치하는지를 반환한다.")
    @Test
    void matchesPassword() {
        final Customer customer = new Customer(1L, new UserName("giron"), new EncryptPassword(plainBasicPassword));
        PasswordEncryptor passwordEncryptor = new PasswordTestEncryptor();

        assertAll(
                () -> assertThat(customer.matchesPassword(passwordEncryptor, plainBasicPassword)).isTrue(),
                () -> assertThat(customer.matchesPassword(passwordEncryptor, plainReversePassword)).isFalse()
        );
    }
}
