package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.domain.exception.InvalidPasswordLengthException;

class PasswordTest {

    @ParameterizedTest
    @DisplayName("평문이 암호화된 비밀번호와 일치하는 지 확인한다.")
    @CsvSource(value = {"12345678,12345678,true", "12345678,87654321,false"})
    void isSamePassword(final String valueToEncrypt, final String rawValue, final boolean expected) {
        // given
        final Password password = Password.fromRawValue(valueToEncrypt);

        // when
        final boolean actual = password.isSamePassword(rawValue);

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("평문이 8자리 미만일 경우 예외가 발생한다.")
    @ValueSource(strings = {"", "썬", "클레이", "1234567"})
    void fromRawValue_lengthLessThanEight_throwsException(final String rawValue) {
        // when, then
        assertThatThrownBy(() -> Password.fromRawValue(rawValue))
                .isInstanceOf(InvalidPasswordLengthException.class);
    }

    @Test
    @DisplayName("평문으로 Password 객체를 생성할 경우 이미 암호화된 값을 받으면 예외가 발생한다.")
    void fromRawValue() {
        // given
        final Password password = Password.fromRawValue("12345678");

        // when, then
        assertThatThrownBy(() -> Password.fromRawValue(password.getHashedValue()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("평문 비밀번호가 입력되어야 합니다.");
    }

    @Test
    @DisplayName("암호화된 값으로 Password 객체를 생성할 경우 평문을 입력 받으면 예외가 발생한다.")
    void fromHashedValue() {
        // given, when, then
        assertThatThrownBy(() -> Password.fromHashedValue("12345678"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("암호화된 비밀번호가 입력되어야 합니다.");
    }
}
