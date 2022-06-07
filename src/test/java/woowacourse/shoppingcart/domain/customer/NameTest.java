package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.InvalidFormException;
import woowacourse.shoppingcart.exception.InvalidLengthException;

class NameTest {

    @Test
    @DisplayName("이름을 생성한다.")
    void createName() {
        String value = "slowchalee";

        assertThatCode(() -> new Name(value))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"abcd", "aaaaabbbbbcccccddddde"})
    @DisplayName("이름의 길이는 5이상 20이하가 아닌 경우, 예외를 발생한다.")
    void invalidLengthException(String value) {
        assertThatExceptionOfType(InvalidLengthException.class)
                .isThrownBy(() -> new Name(value));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidName")
    @DisplayName("영문 소문자, 숫자, 특수기호(_, -) 외의 문자를 사용해서 이름을 생성한 경우, 예외를 발생한다.")
    void invalidFormException(String value) {
        assertThatExceptionOfType(InvalidFormException.class)
                .isThrownBy(() -> new Name(value));
    }

    private static Stream<Arguments> provideInvalidName() {
        return Stream.of(
                Arguments.of("Abcdef"),
                Arguments.of("hello!!"),
                Arguments.of("hello-!"),
                Arguments.of("abc12_@")
        );
    }
}
