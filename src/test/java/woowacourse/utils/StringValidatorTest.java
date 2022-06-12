package woowacourse.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import woowacourse.shoppingcart.exception.UsernameValidationException;

import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class StringValidatorTest {

    static Stream<String> blankStrings() {
        return Stream.of("", "   ", null);
    }

    @Test
    @DisplayName("길이값을 검증한 뒤 조건에 부합하지 않으면 예외를 발생시킨다.")
    void validateLength() {
        assertThatThrownBy(() -> {
            final String longerThanMaxLength = "longerThanMaxLength";
            StringValidator.validateLength(1, 10, longerThanMaxLength, new UsernameValidationException("정해진 길이보다 깁니다."));
        })
                .isInstanceOf(UsernameValidationException.class)
                .hasMessage("정해진 길이보다 깁니다.");
    }

    @ParameterizedTest
    @MethodSource("blankStrings")
    @DisplayName("공백이거나 null일 경우 예외를 발생시킨다.")
    void validateNullOrBlank(String containEmptyString) {
        assertThatThrownBy(() -> {
            StringValidator.validateNullOrBlank(containEmptyString, new UsernameValidationException("정해진 길이보다 깁니다."));
        })
                .isInstanceOf(UsernameValidationException.class)
                .hasMessage("정해진 길이보다 깁니다.");
    }
}
