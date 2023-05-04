package cart.domain.product;

import cart.domain.product.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
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

    @Test
    @DisplayName("이름, 이미지, 가격을 수정한다.")
    void edit_product_success() {
        // given
        Product product = Product.from(1L, "치킨", "URL", 1000);

        // when
        product.edit("피자", "img", 20000);

        // then
        assertAll(
                () -> assertThat(product.getName()).isEqualTo("피자"),
                () -> assertThat(product.getImgUrl()).isEqualTo("img"),
                () -> assertThat(product.getPrice()).isEqualTo(20000)
        );
    }
}
