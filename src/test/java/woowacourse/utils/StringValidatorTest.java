package woowacourse.utils;

import static org.assertj.core.api.Assertions.*;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.ValidationException;

class StringValidatorTest {

    @ParameterizedTest
    @MethodSource("provideForValidateLength")
    @DisplayName("입력받은 최소길이와 최대길이로 문자열을 검증하여 실패시 예외를 발생시킨다.")
    void validateLength(final int minLength, final int maxLength, final String target) {
        assertThatThrownBy(
                () -> StringValidator.validateLength(minLength, maxLength, target, new TestValidationException())
        ).isInstanceOf(ValidationException.class);
    }

    public static Stream<Arguments> provideForValidateLength() {
        return Stream.of(
                Arguments.of(1, 2, ""),
                Arguments.of(0, 1, "aa")
        );
    }

    @ParameterizedTest
    @MethodSource("provideForValidateNullOrBlank")
    @DisplayName("입력받은 문자열이 null 혹은 빈 문자열 혹은 공백만 존재하는 문자열일 경우 예외를 발생시킨다.")
    void validateNullOrBlank(final String target) {
        assertThatThrownBy(
                () -> StringValidator.validateNullOrBlank(target, new TestValidationException())
        ).isInstanceOf(ValidationException.class);
    }

    public static Stream<String> provideForValidateNullOrBlank() {
        return Stream.of("", "  ", null);
    }

    static class TestValidationException extends ValidationException {
        public TestValidationException() {
            super("테스트 실패", "test");
        }
    }
}