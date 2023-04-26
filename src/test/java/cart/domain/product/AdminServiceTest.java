package cart.domain.product;

import cart.domain.product.dto.ProductCreationDto;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

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
        Assertions.assertThat(allProducts).hasSize(1);
        Assertions.assertThat(allProducts.get(0).getName()).isEqualTo("Pizza");
    }
}
