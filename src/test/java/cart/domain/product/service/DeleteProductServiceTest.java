package cart.domain.product.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.domain.product.TestFixture;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class DeleteProductServiceTest {

    @Autowired
    private DeleteOneProductService deleteProductService;

    @Autowired
    private ProductRepository productRepository;

    @DisplayName("단일 상품 삭제 테스트")
    @Test
    void deleteProduct() {
        final Product pizza = TestFixture.PIZZA;
        final Long savedId = productRepository.save(pizza);
        assertThat(productRepository.findAll()).hasSize(1);

        deleteProductService.deleteSingleProductById(savedId);

        final List<Product> allProducts = productRepository.findAll();
        assertThat(allProducts).hasSize(0);
    }
}
