package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.product.dto.ProductCreationDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class AdminServiceTest {

    @Autowired
    private AdminService adminService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    void saveProduct() {
        ProductCreationDto pizza = new ProductCreationDto(
                "Pizza",
                18_000,
                "FOOD",
                "www.domino-pizza.com"
        );

        adminService.save(pizza);

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(1);
        assertThat(allProducts.get(0).getName()).isEqualTo("Pizza");
    }

    @DisplayName("단일 상품 삭제 테스트")
    @Test
    void deleteProduct() {
        Product pizza = TestFixture.PIZZA;
        Long savedId = productRepository.save(pizza);
        assertThat(productRepository.findAll()).hasSize(1);

        adminService.delete(savedId);

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(0);
    }
}
