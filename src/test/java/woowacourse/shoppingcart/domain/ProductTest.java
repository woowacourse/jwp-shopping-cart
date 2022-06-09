package woowacourse.shoppingcart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import woowacourse.shoppingcart.exception.dataformat.ProductDataFormatException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("Product 도메인 테스트")
class ProductTest {

    @ParameterizedTest
    @ValueSource(ints = {-1, 0})
    @DisplayName("상품의 가격이 0 이하일 경우 예외가 발생한다.")
    void priceUnderZero(int price) {
        // when & then
        assertThatThrownBy(() -> new Product("초콜렛", price, "www.test.com"))
                .isInstanceOf(ProductDataFormatException.class)
                .hasMessage("상품의 가격은 0 원 이상이어야 합니다.");
    }

    @DisplayName("상품의 가격이 10 원 단위가 아닐 경우 예외가 발생한다.")
    @Test
    void priceNotMoneyUnit() {
        // when & then
        assertThatThrownBy(() -> new Product("초콜렛", 1234, "www.test.com"))
                .isInstanceOf(ProductDataFormatException.class)
                .hasMessage("상품의 가격은 10원 단위로 입력해주세요.");
    }
}
