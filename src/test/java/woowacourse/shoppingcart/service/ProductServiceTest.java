package woowacourse.shoppingcart.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.ProductRequestDto;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    private ProductRequestDto product1 = new ProductRequestDto("product1", 10000, null, 10);
    private ProductRequestDto product2 = new ProductRequestDto("product2", 11000, null, 10);

    @Test
    @DisplayName("상품 전체 목록을 불러온다.")
    void findProducts() {
        productService.addProduct(product1);
        productService.addProduct(product2);

        List<Product> actual = productService.findProducts();

        assertThat(actual).containsExactly(
                new Product("product1", 10000, null, 10),
                new Product("product2", 11000, null, 10)
        );
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addProduct() {
        Long addedId = productService.addProduct(product1);
        assertThat(productService.findProductById(addedId)).isEqualTo(product1);
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteProductById() {
        Long addedId = productService.addProduct(product1);

        productService.deleteProductById(addedId);

        assertThatThrownBy(() -> productService.findProductById(addedId))
                .isInstanceOf(InvalidProductException.class);
    }

    @Test
    @DisplayName("상품들을 추가한다.")
    void addProducts() {
        List<ProductRequestDto> productRequestDtos = List.of(
                new ProductRequestDto("product1", 10000, null, 10),
                new ProductRequestDto("product2", 11000, null, 10)
        );

        int actual = productService.addProducts(productRequestDtos);

        assertThat(actual).isEqualTo(2);
    }
}
