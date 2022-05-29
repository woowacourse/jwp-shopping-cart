package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import woowacourse.shoppingcart.exception.InvalidPhoneNumberException;

class PhoneNumberTest {

    @Test
    @DisplayName("핸드폰 번호를 생성한다.")
    void createPhoneNumber() {
        String value = "010-1234-5678";

        assertThatCode(() -> new PhoneNumber(value))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPhoneNumber")
    @DisplayName("핸드폰 번호의 형식이 올바르지 않을 경우, 예외를 발생한다.")
    void invalidPhoneNumberException(String value) {
        assertThatExceptionOfType(InvalidPhoneNumberException.class)
                .isThrownBy(() -> new PhoneNumber(value));
    }

    private static Stream<Arguments> provideInvalidPhoneNumber() {
        return Stream.of(
                Arguments.of("016-1234-5678"),
                Arguments.of("01012345678"),
                Arguments.of("010-abcd-5678"),
                Arguments.of("010-123-45678")
        );
    }

    // - 없는 경우
    // 010 으로 시작하지 않는 경우
    // 숫자가 아닌 문자를 입력한 경우
    // 3, 4, 4 길이가 아닌 경우
}
