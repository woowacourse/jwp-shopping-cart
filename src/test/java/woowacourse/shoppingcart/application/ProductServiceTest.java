package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.tuple;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    private Product 칫솔;
    private Product 치약;
    private Product 비누;
    private Long notFoundId;

    @BeforeEach
    void setUp() {
        칫솔 = productService.save(new ProductRequest("칫솔", 1600, "image 칫솔"));
        치약 = productService.save(new ProductRequest("치약", 2200, "image 치약"));
        비누 = productService.save(new ProductRequest("비누", 4300, "image 비누"));
        notFoundId = Stream.of(칫솔, 치약, 비누)
            .mapToLong(Product::getId)
            .max()
            .orElse(0L) + 1L;
    }

    @DisplayName("상품 목록 조회")
    @Test
    void findProducts() {
        List<Product> products = productService.findProducts();

        assertAll(
            () -> assertThat(products).hasSize(3),
            () -> assertThat(products)
                .usingRecursiveComparison()
                .isEqualTo(List.of(칫솔, 치약, 비누))
        );
    }

    @DisplayName("상품 조회")
    @Test
    void findProduct() {
        Product product = productService.findProductById(치약.getId());

        assertThat(product)
            .usingRecursiveComparison()
            .isEqualTo(치약);
    }

    @DisplayName("존재하지 않는 상품 조회 시 예외 발생")
    @Test
    void findNotExistProduct() {
        assertThatThrownBy(() -> productService.findProductById(notFoundId))
            .isInstanceOf(NotFoundProductException.class);
    }
}
