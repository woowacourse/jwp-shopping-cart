package cart.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.product.TestFixture;
import cart.product.domain.Product;
import cart.product.domain.ProductRepository;
import cart.product.service.FindAllProductService;
import cart.product.service.dto.ProductResponseDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class FindAllProductServiceTest {

    @Autowired
    private FindAllProductService findAllProductService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품 전체 조회 테스트")
    @Test
    void getAllProducts() {
        final Product pizza = TestFixture.PIZZA;
        final Product chicken = TestFixture.CHICKEN;
        productRepository.save(pizza);
        productRepository.save(chicken);

        final List<ProductResponseDto> allProducts = findAllProductService.getAllProducts();

        assertThat(allProducts).hasSize(2);
        assertThat(allProducts).extractingResultOf("getName")
                .contains("Pizza", "Chicken");
    }
}
