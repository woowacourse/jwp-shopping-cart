package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PhoneNumberTest {

    @DisplayName("핸드폰 번호 길이가 11자이면 핸드폰 번호가 생성된다.")
    @Test
    void makePhoneNumber() {
        assertThat(new PhoneNumber("01012345678")).isNotNull();
    }

    @DisplayName("핸드폰 번호가 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwWhenPhoneNumberNullOrEmpty(String phoneNumber) {
        assertThatThrownBy(() ->
                new PhoneNumber(phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("핸드폰 번호는 비어있을 수 없습니다.");
    }

    @DisplayName("핸드폰 번호의 길이가 11자가 아니라면 예외를 발생한다.")
    @ParameterizedTest
    @CsvSource(value = {"0101234567", "010123456789"})
    void throwWhenInvalidPhoneLength(String phoneNumber) {
        assertThatThrownBy(() ->
                new PhoneNumber(phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("핸드폰 번호 길이는 11자 이어야 합니다.");
    }
}
