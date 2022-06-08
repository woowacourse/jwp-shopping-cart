package woowacourse.shoppingcart.domain.customer.address;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class FullAddressTest {
    @DisplayName("전체 주소 문자열을 전달받아 생성된다.")
    @Test
    void constructor() {
        // given
        String address = "서울시 강남구 선릉역";
        String detailAddress = "이디야";
        String zonecode = "12345";

        // when
        FullAddress actual = FullAddress.of(address, detailAddress, zonecode);

        // then
        assertThat(actual).isNotNull();
    }

    @DisplayName("올바르지 않은 주소 포맷을 전달하면 예외가 발생한다.")
    @CsvSource(value = {",이디야,12345", "서울시 강남구,이디야,1234", ",,"})
    @ParameterizedTest
    void constructor_invalidFormat(String address, String detailAddress, String zonecode) {
        // when & then
        assertThatThrownBy(() -> FullAddress.of(address, detailAddress, zonecode))
                .isInstanceOf(RuntimeException.class);
    }

}
