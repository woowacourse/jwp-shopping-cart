package cart.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.product.TestFixture;
import cart.product.domain.Product;
import cart.product.domain.ProductCategory;
import cart.product.domain.ProductRepository;
import cart.product.service.FixOneProductInfoService;
import cart.product.service.dto.ProductModificationDto;
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
class FixProductInfoServiceTest {

    @Autowired
    private FixOneProductInfoService fixProductInfoService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("상품 수정 테스트")
    @Test
    void updateProduct() {
        final Product pizza = TestFixture.PIZZA;
        final Long savedId = productRepository.save(pizza);
        final ProductModificationDto updatedProduct = new ProductModificationDto(
                savedId,
                "Chicken",
                20_000,
                "FOOD",
                "chicken.com"
        );

        fixProductInfoService.fixSingleProductInfo(updatedProduct);

        final List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(1);
        final Product savedProduct = allProducts.get(0);
        assertThat(savedProduct.getProductId()).isEqualTo(savedId);
        assertThat(savedProduct.getName()).isEqualTo("Chicken");
        assertThat(savedProduct.getPrice()).isEqualTo(BigDecimal.valueOf(20_000));
        assertThat(savedProduct.getCategory()).isEqualTo(ProductCategory.FOOD);
        assertThat(savedProduct.getImageUrl()).isEqualTo("chicken.com");
    }
}
