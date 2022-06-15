package woowacourse.shoppingcart.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.shoppingcart.customer.support.exception.CustomerException;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;

class PasswordTest {

    @DisplayName("비밀번호는 최소 10자리 이상으로 영문자, 숫자, 특수문자의 조합이어야 한다")
    @ParameterizedTest
    @ValueSource(strings = {
            "123456789", "12345678a", "12345678@",
            "1234567890", "1234567890aaa", "1234567890!@#"
    })
    void validatePassword(final String password) {
        assertThatThrownBy(() -> new Password(password))
                .isInstanceOf(CustomerException.class)
                .extracting("exceptionCode")
                .isEqualTo(CustomerExceptionCode.INVALID_FORMAT_PASSWORD);
    }

    @DisplayName("비밀번호 VO 끼리 서로 일치하는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"1234567890a!,1234567890a!,true", "1234567890a!,1234567890f^,false"})
    void equals(final String source, final String target, final boolean expected) {
        final Password sourcePassword = new Password(source);
        final Password targetPassword = new Password(target);
        assertThat(sourcePassword.equals(targetPassword)).isEqualTo(expected);
    }

    @DisplayName("비밀번호 VO 의 값과 일치하는지 확인한다.")
    @ParameterizedTest
    @CsvSource(value = {"1234567890a!,1234567890a!,true", "1234567890a!,12345,false"})
    void equalsValue(final String source, final String target, final boolean expected) {
        final Password sourcePassword = new Password(source);
        assertThat(sourcePassword.equalsValue(target)).isEqualTo(expected);
    }
}