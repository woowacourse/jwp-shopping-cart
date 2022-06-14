package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddressTest {

    @DisplayName("주소가 255자 이하이면 주소를 생성한다.")
    @Test
    void makeAddress() {
        final String value = "a".repeat(255);

        assertThat(new Address(value)).isNotNull();
    }

    @DisplayName("주소가 비어있으면 예외를 발생한다.")
    @ParameterizedTest
    @NullAndEmptySource
    void throwWhenAddressNullOrEmpty(String address) {
        assertThatThrownBy(() ->
                new Address(address))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소는 비어있을 수 없습니다.");
    }

    @DisplayName("주소 길이가 255자를 초과하면 예외를 발생한다.")
    @Test
    void throwWhenInvalidAddressLength() {
        final String address = "a".repeat(256);
        assertThatThrownBy(() ->
                new Address(address))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소 길이는 255자를 초과할 수 없습니다.");
    }
}
