package cart.service;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import cart.exception.ProductNotFoundException;
import cart.repository.JdbcProductRepository;
import cart.service.dto.ProductRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@JdbcTest
@Import({JdbcProductRepository.class, ProductService.class})
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Nested
    class updateProduct {
        @Test
        @DisplayName("업데이트하려는 상품이 없는 경우 ProductNotFoundException 발생")
        void productNotFound() {
            final ProductRequest appleRequest = new ProductRequest(10L, "사과", "apple.png", 2000);

            assertThatThrownBy(() -> productService.updateProduct(appleRequest))
                    .isInstanceOf(ProductNotFoundException.class);
        }
    }
}
