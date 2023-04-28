package cart.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.domain.product.TestFixture;
import cart.domain.product.service.dto.ProductDto;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("모든 상품들을 조회할 수 있다.")
    @Test
    void getAllProducts() {
        Product pizza = TestFixture.PIZZA;
        Product chicken = TestFixture.CHICKEN;
        productRepository.save(pizza);
        productRepository.save(chicken);

        List<ProductDto> allProducts = cartService.getAllProducts();

        assertThat(allProducts).hasSize(2);
        assertThat(allProducts).extractingResultOf("getName")
                .contains("Pizza", "Chicken");
    }
}
