package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowacourse.shoppingcart.domain.customer.Address;

class AddressTest {

    @Test
    @DisplayName("주소를 생성한다.")
    void createAddress() {
        //given
        Address address = new Address("에덴동산");
        //when

        //then
        assertThat(address.getValue()).isEqualTo("에덴동산");
    }

    @Test
    void invalidAddressLength() {
        //given
        //when
        //then
        assertThatThrownBy(() -> new Address("a".repeat(256)))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소 길이는 255자를 초과할 수 없습니다.");
    }
}
