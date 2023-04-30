package cart.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class PhoneNumberTest {

    @Test
    @DisplayName("전화번호가 숫자로만 이루어지고 길이가 10자 이상 20자 이하면 정상 생성됩니다")
    void acceptedPhoneNumber() {
        assertAll(
                () -> assertThatNoException().isThrownBy(() -> new PhoneNumber("1".repeat(10))),
                () -> assertThatNoException().isThrownBy(() -> new PhoneNumber("1".repeat(20)))
        );
    }

    @Test
    @DisplayName("전화번호가 숫자로만 이루어지지 않거나 길이가 10자 미만 20자 초과면 예외를 발생시킵니다")
    void unacceptablePhoneNumber() {
        String notOnlyNumber = "a1234567890";
        String tooShortNumber = "1".repeat(9);
        String tooLongNumber = "1".repeat(21);

        assertAll(
                () -> assertThatThrownBy(() -> new PhoneNumber(notOnlyNumber))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("숫자만 입력해주세요"),
                () -> assertThatThrownBy(() -> new PhoneNumber(tooShortNumber))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("전화번호 길이는 10자 이상 20자 이하입니다"),
                () -> assertThatThrownBy(() -> new PhoneNumber(tooLongNumber))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("전화번호 길이는 10자 이상 20자 이하입니다")
        );
    }
}

