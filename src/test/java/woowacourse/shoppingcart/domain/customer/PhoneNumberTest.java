package woowacourse.shoppingcart.domain.customer;

import static Fixture.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class PhoneNumberTest {

    @DisplayName("phoneNumber가 null이면 예외를 던진다.")
    @Test
    void create_error_null() {
        assertThatThrownBy(() -> new PhoneNumber(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("전화번호는 필수 입력 사항압니다.");
    }

    @DisplayName("phoneNumber 형식이 맞지 않으면 예외를 던진다.")
    @ParameterizedTest
    @ValueSource(strings = {"0000-0000-0000", "-0000-0000", "000-0000", "0000"})
    void create_error_phoneNumberFormat(String phoneNumber) {
        assertThatThrownBy(() -> new PhoneNumber(phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("전화번호 형식이 올바르지 않습니다. (형식: 000-0000-0000)");
    }

    @DisplayName("phoneNumber 형식에 맞으면 phoneNumber가 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {MAT_PHONE_NUMBER, YAHO_PHONE_NUMBER})
    void create(String phoneNumber) {
        assertDoesNotThrow(() -> new PhoneNumber(phoneNumber));
    }
}
