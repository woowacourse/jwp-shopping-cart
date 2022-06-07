package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductTest {

    @Test
    @DisplayName("상품 가격은 0이하의 값으로 생성할 수 없다.")
    void createProduct() {
        // given
        Long id = 1L;
        String name = "상품1";
        int price = 0;
        String imageUrl = "https://~~";

        // when & then
        assertThatThrownBy(() -> new Product(id, name, price, imageUrl))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
