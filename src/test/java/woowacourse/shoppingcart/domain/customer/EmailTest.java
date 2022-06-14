package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import woowacourse.shoppingcart.exception.InvalidFormException;

class EmailTest {

    @Test
    @DisplayName("이메일을 생성한다.")
    void createEmail() {
        String value = "testemail@email.com";

        assertThatCode(() -> new Email(value))
                .doesNotThrowAnyException();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidEmail")
    @DisplayName("이메일 형식이 잘못된 경우, 예외를 발생한다.")
    void invalidFormException(String value) {
        assertThatExceptionOfType(InvalidFormException.class)
                .isThrownBy(() -> new Email(value));
    }

    private static Stream<Arguments> provideInvalidEmail() {
        return Stream.of(
                Arguments.of("Example@test.com"),
                Arguments.of("example!@test.com"),
                Arguments.of("exam12@@test.com"),
                Arguments.of("example@Test.com"),
                Arguments.of("example@test@.com"),
                Arguments.of("example@test1.com"),
                Arguments.of("example@test.KR"),
                Arguments.of("example@test.krrr"),
                Arguments.of("example@test...."),
                Arguments.of("example@test.c0m"),
                Arguments.of("example@test.c")
        );
    }
}
