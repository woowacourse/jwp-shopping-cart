package cart.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.product.domain.Product;
import cart.product.domain.ProductRepository;
import cart.product.service.EnrollOneProductService;
import cart.product.service.dto.ProductCreationDto;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class EnrollOneProductServiceTest {

    @Autowired
    private EnrollOneProductService enrollOneProductService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("단일 상품 저장 테스트")
    @Test
    void saveProduct() {
        final ProductCreationDto pizza = new ProductCreationDto(
                "Pizza",
                18_000,
                "FOOD",
                "www.domino-pizza.com"
        );

        enrollOneProductService.enroll(pizza);

        final List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(1);
        assertThat(allProducts.get(0).getName()).isEqualTo("Pizza");
    }

}
