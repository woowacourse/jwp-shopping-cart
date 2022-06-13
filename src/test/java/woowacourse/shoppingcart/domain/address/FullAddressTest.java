package woowacourse.shoppingcart.domain.address;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import woowacourse.auth.exception.BadRequestException;

@DisplayName("FullAddress 는")
class FullAddressTest {

    @DisplayName("주소를 입력했을 때 ")
    @Nested
    class FullAddressValidationTest {

        @DisplayName("입력한 우편번호가 유효하면 저장한다.")
        @Test
        void validZoneCode() {
            assertThatNoException().isThrownBy(() -> new FullAddress("a", "b", "12345"));
        }

        @DisplayName("입력한 우편번호가 유효하지 않으면 예외를 던진다.")
        @Test
        void invalidZoneCode() {
            assertThatThrownBy(() -> new FullAddress("a", "b", "1234"))
                    .isInstanceOf(BadRequestException.class)
                    .hasMessage(FullAddress.INVALID_ZONE_CODE_FORMAT);
        }
    }
}
