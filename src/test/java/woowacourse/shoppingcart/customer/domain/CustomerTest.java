package woowacourse.shoppingcart.customer.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static woowacourse.support.TextFixture.EMAIL;
import static woowacourse.support.TextFixture.NICKNAME;
import static woowacourse.support.TextFixture.PASSWORD;
import static woowacourse.support.TextFixture.PASSWORD_VALUE;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.shoppingcart.customer.support.exception.CustomerException;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;

class CustomerTest {

    @DisplayName("개인정보를 수정한다.")
    @ParameterizedTest
    @ValueSource(strings = {"nickname"})
    void updateProfile(final String expected) {
        final Customer customer = new Customer(EMAIL, NICKNAME, PASSWORD);
        customer.updateProfile(expected);

        assertThat(customer.getNickname()).isEqualTo(expected);
    }

    @DisplayName("기존 비밀번호가 일치하지 않으면 비밀번호를 수정할 수 없다.")
    @Test
    void updatePasswordWhenMisMatches() {
        final Customer customer = new Customer(EMAIL, NICKNAME, PASSWORD);
        assertThatThrownBy(() -> customer.updatePassword("WRONG" + PASSWORD_VALUE, PASSWORD_VALUE))
                .isInstanceOf(CustomerException.class)
                .extracting("exceptionCode")
                .isEqualTo(CustomerExceptionCode.MISMATCH_PASSWORD);
    }

    @DisplayName("기존 비밀번호가 일치하면 비밀번호를 수정할 수 있다")
    @ParameterizedTest
    @ValueSource(strings = {"newQWE123!@#"})
    void updatePasswordWhenMatches(final String newPassword) {
        final Customer customer = new Customer(EMAIL, NICKNAME, PASSWORD);
        customer.updatePassword(PASSWORD, new Password(newPassword));

        assertThat(customer.getPassword()).isEqualTo(newPassword);
    }

    @DisplayName("비밀번호가 기존의 비밀번호와 다른지 확인한다")
    @ParameterizedTest
    @CsvSource(value = {"qwer1234!@#$,qwer1234!@#$,false", "qwer1234!@#$,qwer5678!@#$,true"})
    void isPasswordDisMatch(final String source, final String target, final boolean expected) {
        final Customer customer = new Customer(EMAIL, NICKNAME, new Password(source));
        assertThat(customer.isPasswordDisMatch(target)).isEqualTo(expected);
    }
}