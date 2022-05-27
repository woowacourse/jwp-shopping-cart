package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PhoneNumberTest {

    @ParameterizedTest
    @ValueSource(strings = {"010", "010123456789"})
    @DisplayName("핸드폰 번호가 11자가 아닐 경우 예외 발생")
    void invalidPhoneNumberLength_throwException(String phoneNumber) {
        assertThatThrownBy(() -> new PhoneNumber(phoneNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"0101234567*", "010abcdefg"})
    @DisplayName("핸드폰 번호가 숫자가 아닐 경우 예외 발생")
    void invalidPhoneNumberPattern_throwException(String phoneNumber) {
        assertThatThrownBy(() -> new PhoneNumber(phoneNumber))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
