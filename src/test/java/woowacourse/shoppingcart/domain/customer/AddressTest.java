package woowacourse.shoppingcart.domain.customer;

import static Fixture.CustomerFixtures.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AddressTest {

    @DisplayName("address가 null이면 예외를 던진다.")
    @Test
    void create_error_null() {
        assertThatThrownBy(() -> new Address(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("주소는 필수 입력 사항입니다.");
    }

    @DisplayName("address 형식이 맞지 않으면 예외를 던진다.")
    @Test
    void create_error_addressFormat() {
        String address = "a";
        assertThatThrownBy(() -> new Address(address.repeat(256)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소 형식이 올바르지 않습니다. (길이: 255 이하)");
    }

    @DisplayName("address 형식에 맞으면 address가 생성된다.")
    @ParameterizedTest
    @ValueSource(strings = {MAT_ADDRESS, YAHO_ADDRESS})
    void create(String address) {
        assertDoesNotThrow(() -> new Address(address));
    }
}
