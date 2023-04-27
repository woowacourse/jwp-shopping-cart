package cart.domain.product;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.product.dto.ProductCreationDto;
import cart.domain.product.dto.ProductModificationDto;
import java.math.BigDecimal;
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

    @DisplayName("상품 수정 테스트")
    @Test
    void updateProduct() {
        Product pizza = TestFixture.PIZZA;
        Long savedId = productRepository.save(pizza);
        ProductModificationDto updatedProduct = new ProductModificationDto(
                savedId,
                "Chicken",
                20_000,
                "FOOD",
                "chicken.com"
        );

        adminService.update(updatedProduct);

        List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(1);
        Product savedProduct = allProducts.get(0);
        assertThat(savedProduct.getProductId()).isEqualTo(savedId);
        assertThat(savedProduct.getName()).isEqualTo("Chicken");
        assertThat(savedProduct.getPrice()).isEqualTo(BigDecimal.valueOf(20_000));
        assertThat(savedProduct.getCategory()).isEqualTo(ProductCategory.FOOD);
        assertThat(savedProduct.getImageUrl()).isEqualTo("chicken.com");
    }
}
