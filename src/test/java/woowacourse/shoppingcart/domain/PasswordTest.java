package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.util.BcryptConvertor;

class PasswordTest {

    private static final PasswordConvertor PASSWORD_CONVERTOR = new BcryptConvertor();

    @ParameterizedTest
    @DisplayName("평문이 암호화된 비밀번호와 일치하는 지 확인한다.")
    @CsvSource(value = {"12345678,12345678,true", "12345678,87654321,false"})
    void isSamePassword(final String valueToEncrypt, final String rawValue, final boolean expected) {
        // given
        final Password password = Password.fromRawValue(valueToEncrypt, PASSWORD_CONVERTOR);

        // when
        final boolean actual = password.isSamePassword(rawValue, PASSWORD_CONVERTOR);

        // then
        assertThat(actual).isEqualTo(expected);
    }
}
