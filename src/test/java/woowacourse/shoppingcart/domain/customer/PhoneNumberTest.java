package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PhoneNumberTest {

    @DisplayName("PhoneNumber를 생성해야 합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"01012341234", "01112341234", "01612341234"})
    void createPhoneNumber(final String value) {
        final PhoneNumber phoneNumber = new PhoneNumber(value);

        assertThat(phoneNumber.getValue()).isEqualTo(value);
    }

    @DisplayName("PhoneNumber가 패턴에 맞지 않은경우 예외를 던져야 합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"01212341234", "01512341234", "공일공일이삼사일이삼사", "010-1234-1234", "02-123-1234"})
    void createInvalidPhoneNumber(final String value) {
        assertThatThrownBy(() -> new PhoneNumber(value))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("phoneNumber를 변경한다.")
    @Test
    void update() {
        final PhoneNumber phoneNumber = new PhoneNumber("01012341234");
        final String newPhoneNumber = "01011112222";
        final PhoneNumber updatePhoneNumber = phoneNumber.update(newPhoneNumber);

        assertThat(updatePhoneNumber.getValue()).isEqualTo(newPhoneNumber);
    }
}
