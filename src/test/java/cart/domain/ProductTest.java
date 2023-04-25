package cart.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ProductTest {

    @Test
    @DisplayName("id, 이름, 이미지, 가격으로 생성한다.")
    void create_product_success() {
        // given
        Long id = 1L;
        String name = "치킨";
        String imgUrl = "img";
        int price = 10000;

        // when & then
        assertDoesNotThrow(
                () -> Product.from(id, name, imgUrl, price)
        );
    }
}
