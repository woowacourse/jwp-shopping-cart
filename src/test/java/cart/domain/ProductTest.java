package cart.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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

    @DisplayName("아이디가 다르면 다른 상품이다.")
    @Test
    void notEquals() {
        // given
        String name = "상품명1";
        int price = 100000;
        String imgUrl = "https://www.shutterstock.com/ko/image-illustration/3d-gift-box-pink-valentine-illustration-2257249003";
        Product product = Product.from(1L, name, imgUrl, price);
        Product otherProduct = Product.from(2L, name, imgUrl, price);

        // when & then
        assertThat(product).isNotEqualTo(otherProduct);
    }

    @DisplayName("아이디가 같으면 동일한 상품이다.")
    @Test
    void equals() {
        // given
        Long id = 1L;
        String imgUrl = "https://www.shutterstock.com/ko/image-illustration/3d-gift-box-pink-valentine-illustration-2257249003";
        Product product = Product.from(id, "상품명1", imgUrl, 100000);
        Product otherProduct = Product.from(id, "수정 상품명", imgUrl, 10000000);

        // when & then
        assertThat(product).isEqualTo(otherProduct);
    }
}
