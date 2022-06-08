package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import woowacourse.shoppingcart.domain.customer.PhoneNumber;

class PhoneNumberTest {

    @Test
    @DisplayName("휴대폰 번호 객체를 생성한다.")
    void createPhoneNumber() {
        //given
        String rawPhoneNumber = "01012345678";
        PhoneNumber phoneNumber = new PhoneNumber("01012345678");
        //when

        //then
        assertThat(phoneNumber.getValue()).isEqualTo("01012345678");
    }

    @ParameterizedTest
    @CsvSource(value = {"0112341234", "010112341234", "0101231234", "010123451234", "0101234123", "010123412345"})
    @DisplayName("휴대폰 번호 길이가 11자가 아니면 예외를 반환한다.")
    void validateLength(String phoneNumber) {
        //given
        //when
        //then
        assertThatThrownBy(() -> new PhoneNumber(phoneNumber))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("휴대폰번호 형식이 일치하지 않습니다.");
    }

    @Test
    @DisplayName("휴대폰 번호가 숫자가 아니면 예외를 반환한다.")
    void validateDigit() {
        //given
        String notDigit = "abcdefg1234";
        //when

        //then
        assertThatThrownBy(() -> new PhoneNumber(notDigit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("휴대폰 번호는 숫자만 가능합니다.");
    }
}
