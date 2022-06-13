package woowacourse.shoppingcart.domain.customer.values;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class AddressTest {
    
    @DisplayName("주소가 비어있거나, 256자 이상이면 예외가 발생한다.")
    @ParameterizedTest
    @ValueSource(strings = {" ", "123456789012345678901234567890123456789012345678901234567890123456789012345678901"})
    void address_blankOrOverLengthLimit_ThrowException(String address) {
        assertThatThrownBy(() -> new Address(address))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("주소는 1자 이상, 80자 이하여야 합니다.");
    }

}