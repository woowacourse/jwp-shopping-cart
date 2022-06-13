package woowacourse.shoppingcart.domain.customer.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.format.InvalidZonecodeFormatException;

class ZonecodeTest {
    @DisplayName("우편번호 문자열을 전달받아 생성된다.")
    @Test
    void constructor() {
        // given
        String zonecode = "12345";

        // when
        Zonecode actual = new Zonecode(zonecode);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("올바르지 않은 우편번호 포맷을 전달하면 예외가 발생한다.")
    @ValueSource(strings = {"", "1234", "123456", "a1234"})
    @ParameterizedTest
    void constructor_invalidFormat(String input) {
        // when & then
        assertThatThrownBy(() -> new Zonecode(input))
                .isInstanceOf(InvalidZonecodeFormatException.class);
    }
}
