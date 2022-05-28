package woowacourse.auth.domain.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.auth.exception.InvalidAddressFormatException;

class AddressTest {
    @DisplayName("주소 문자열을 전달받아 생성된다.")
    @Test
    void constructor() {
        // given
        String address = "서울시 강남구 선릉역";

        // when
        Address actual = new Address(address);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("올바르지 않은 주소 포맷을 전달하면 예외가 발생한다.")
    @ValueSource(strings = {"", " ", "   "})
    @ParameterizedTest
    void constructor_invalidFormat(String input) {
        // when & then
        assertThatThrownBy(() -> new Address(input))
                .isInstanceOf(InvalidAddressFormatException.class);
    }
}
