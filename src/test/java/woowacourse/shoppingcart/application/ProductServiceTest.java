package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponses;
import woowacourse.shoppingcart.exception.InvalidProductException;

@SpringBootTest
@Sql("/sql/ClearCart.sql")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("상품 추가")
    @Test
    void addProduct() {
        // given
        String name = "apple";
        int price = 1000;
        String imageUrl = "imageUrl.jpa";

        // when
        Long id = productService.addProduct(new Product(name, price, imageUrl));

        // then
        assertAll(
                () -> assertThat(productService.findProductById(id).getName()).isEqualTo(name),
                () -> assertThat(productService.findProductById(id).getPrice()).isEqualTo(price),
                () -> assertThat(productService.findProductById(id).getImageUrl()).isEqualTo(imageUrl)
        );
    }

    @DisplayName("단일 상품 조회")
    @Test
    void findProductById() {
        // given
        String name = "apple";
        int price = 1000;
        String imageUrl = "imageUrl.jpa";
        Long id = productService.addProduct(new Product(name, price, imageUrl));

        // when
        Product product = productService.findProductById(id);

        // then
        assertAll(
                () -> assertThat(product.getName()).isEqualTo(name),
                () -> assertThat(product.getPrice()).isEqualTo(price),
                () -> assertThat(product.getImageUrl()).isEqualTo(imageUrl)
        );
    }

    @DisplayName("모든 상품 조회")
    @Test
    void findProducts() {
        // given
        productService.addProduct(new Product("apple", 1000, "imageUrl1.jpg"));
        productService.addProduct(new Product("banana", 2000, "imageUrl2.jpg"));
        productService.addProduct(new Product("graph", 3000, "imageUrl3.jpg"));

        // when
        ProductResponses products = productService.findProducts(10, 1);

        // then
        assertThat(products.getProducts()).hasSize(3);
    }

    @DisplayName("상품 제거")
    @Test
    void deleteProductById() {
        // given
        String name = "apple";
        int price = 1000;
        String imageUrl = "imageUrl.jpa";
        Long id = productService.addProduct(new Product(name, price, imageUrl));

        // when
        productService.deleteProductById(id);

        // then
        assertThatThrownBy(() -> productService.findProductById(id))
                .isInstanceOf(InvalidProductException.class);
    }
}
