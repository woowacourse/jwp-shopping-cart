package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.ProductRequestDto;
import woowacourse.shoppingcart.dto.ProductResponseDto;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = {"classpath:test.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    private final ProductRequestDto product1 = new ProductRequestDto("product1", 10000, null, 10);
    private final ProductRequestDto product2 = new ProductRequestDto("product2", 11000, null, 10);

    @Test
    @DisplayName("상품 전체 목록을 불러온다.")
    void findProducts() {
        productService.addProduct(product1);
        productService.addProduct(product2);

        final List<ProductResponseDto> actual = productService.findProducts();

        assertThat(actual).extracting("name")
                .contains("product1", "product2");
    }

    @Test
    @DisplayName("상품을 추가한다.")
    void addProduct() {
        final Long addedId = productService.addProduct(product1);
        final ProductResponseDto productDto = productService.findProductById(addedId);
        assertThat(productDto.getName()).isEqualTo(product1.getName());
    }

    @Test
    @DisplayName("상품을 삭제한다.")
    void deleteProductById() {
        final Long addedId = productService.addProduct(product1);

        productService.deleteProductById(addedId);

        assertThatThrownBy(() -> productService.findProductById(addedId))
                .isInstanceOf(NotFoundProductException.class)
                .hasMessage("존재하지 않는 상품 ID입니다.");
    }

    @Test
    @DisplayName("상품들을 추가한다.")
    void addProducts() {
        final List<ProductRequestDto> productRequestDtos = List.of(
                new ProductRequestDto("product1", 10000, null, 10),
                new ProductRequestDto("product2", 11000, null, 10)
        );

        int actual = productService.addProducts(productRequestDtos);

        assertThat(actual).isEqualTo(2);
    }
}
