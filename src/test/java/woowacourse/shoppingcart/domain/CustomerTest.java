package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.auth.exception.PasswordNotMatchException;

class CustomerTest {

    private static final String NAME = "클레이";
    private static final String RAW_EMAIL = "clay@gmail.com";
    private static final Email EMAIL = new Email(RAW_EMAIL);
    private static final String RAW_PASSWORD = "12345678";
    private static final PlainPassword PLAIN_PASSWORD = new PlainPassword(RAW_PASSWORD);
    private static final EncodedPassword ENCODED_PASSWORD = new EncodedPassword(PLAIN_PASSWORD.encode());

    @DisplayName("비밀번호가 일치하지 않으면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {"12345677", "1312321312323"})
    void checkPasswordMatch_incorrectPassword_throwsException(final String password) {
        // given
        final Customer customer = new Customer(NAME, EMAIL, ENCODED_PASSWORD);

        // when, then
        assertThatThrownBy(() -> customer.checkPasswordMatch(password))
                .isInstanceOf(PasswordNotMatchException.class);
    }

    @DisplayName("비밀번호가 일치하는지 확인한다.")
    @Test
    void checkPasswordMatch() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, ENCODED_PASSWORD);

        // when, then
        assertThatCode(() -> customer.checkPasswordMatch(RAW_PASSWORD))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("이름을 변경한다.")
    void updateName() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, ENCODED_PASSWORD);
        final String newName = "썬";

        // when
        final Customer actual = customer.updateName(newName);

        // then
        assertThat(actual.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("비밀번호를 변경한다.")
    void updatePassword() {
        // given
        final Customer customer = new Customer(NAME, EMAIL, ENCODED_PASSWORD);
        final PlainPassword plainPassword = new PlainPassword("1234567890");
        final EncodedPassword newPassword = new EncodedPassword(plainPassword.encode());

        // when
        final Customer actual = customer.updatePassword(newPassword);

        // then
        assertThat(actual.getPassword()).isEqualTo(newPassword);
    }
}
