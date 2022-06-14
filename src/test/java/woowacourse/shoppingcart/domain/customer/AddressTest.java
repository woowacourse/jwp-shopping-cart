package woowacourse.shoppingcart.domain.customer;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.exception.InvalidLengthException;

class AddressTest {

    @Test
    @DisplayName("주소를 생성한다.")
    void createAddress() {
        String value = "루터회관 14층";

        assertThatCode(() -> new Address(value))
                .doesNotThrowAnyException();
    }

    @Test
    @DisplayName("주소의 최대 길이인 255자를 넘으면, 예외를 발생한다.")
    void invalidLengthException() {
        String value = IntStream.rangeClosed(0, 255)
                .mapToObj(i -> "x")
                .collect(Collectors.joining());

        assertThatExceptionOfType(InvalidLengthException.class)
                .isThrownBy(() -> new Address(value));
    }
}
