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

class PasswordTest {

    @Test
    @DisplayName("패스워드를 생성한다.")
    void createPassword() {
        String value = "123abcdefg!!";

        assertThatCode(() -> new Password(value))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @ValueSource(strings = {"12345a!", "1234567891234567!"})
    @DisplayName("패스워드의 길이가 올바르지 않은 경우, 예외를 발생한다.")
    void invalidLengthException(String value) {
        assertThatExceptionOfType(InvalidLengthException.class)
                .isThrownBy(() -> new Password(value));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidPasswords")
    @DisplayName("패스워드의 형식이 올바르지 않은 경우, 예외를 발생한다.")
    void invalidFormException(String value) {
        assertThatExceptionOfType(InvalidFormException.class)
                .isThrownBy(() -> new Password(value));
    }


    private static Stream<Arguments> provideInvalidPasswords() {
        return Stream.of(
                Arguments.of("12345678"),
                Arguments.of("abcdefgh"),
                Arguments.of("!@#$%^&*"),
                Arguments.of("1234abcd"),
                Arguments.of("abcd!@#$"),
                Arguments.of("!@#$abcd")
        );
    }
}
