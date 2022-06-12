package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import woowacourse.shoppingcart.exception.attribute.InvalidFormException;

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
        assertThatExceptionOfType(InvalidFormException.class)
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
}
