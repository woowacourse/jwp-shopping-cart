package woowacourse.customer.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import woowacourse.customer.exception.InvalidPhoneNumberException;

class PhoneNumberTest {

    private final String originalPhoneNumber = "01012345678";
    private final String newPhoneNumber = "01699998888";
    private final String wrongPhoneNumber = "016-9999-8888";

    @DisplayName("PhoneNumber를 생성해야 합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"01012341234", "01112341234", "01612341234"})
    void createPhoneNumber(final String value) {
        assertDoesNotThrow(() -> new PhoneNumber(value));
    }

    @DisplayName("PhoneNumber가 패턴에 맞지 않은경우 예외를 던져야 합니다.")
    @ParameterizedTest
    @ValueSource(strings = {"01212341234", "01512341234", "공일공일이삼사일이삼사", "010-1234-1234", "02-123-1234"})
    void createInvalidPhoneNumber(final String value) {
        assertThatThrownBy(() -> new PhoneNumber(value))
            .isInstanceOf(InvalidPhoneNumberException.class)
            .hasMessage("전화번호를 정확히 입력해주세요.");
    }

    @DisplayName("phoneNumber를 변경한다.")
    @Test
    void update() {
        final PhoneNumber phoneNumber = new PhoneNumber(originalPhoneNumber);

        assertDoesNotThrow(() -> phoneNumber.update(newPhoneNumber));
    }

    @DisplayName("잘못된 값으로 phoneNumber를 변경하는 경우 예외를 반환한다.")
    @Test
    void updateException() {
        final PhoneNumber phoneNumber = new PhoneNumber(originalPhoneNumber);

        assertThatThrownBy(() -> phoneNumber.update(wrongPhoneNumber))
            .isInstanceOf(InvalidPhoneNumberException.class)
            .hasMessage("전화번호를 정확히 입력해주세요.");
    }
}
