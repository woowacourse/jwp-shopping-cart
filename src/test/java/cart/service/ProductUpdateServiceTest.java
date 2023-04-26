package cart.service;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import cart.domain.Product;
import cart.repository.StubProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductUpdateServiceTest {

    private StubProductRepository stubProductRepository;
    private ProductUpdateService productUpdateService;

    @BeforeEach
    void setUp() {
        this.stubProductRepository = new StubProductRepository();
        this.productUpdateService = new ProductUpdateService(stubProductRepository);
    }

    @Test
    void 업데이트_테스트() {
        final Product product = stubProductRepository.save(new Product("오도", "naver.com", 1));
        final Product result = productUpdateService.update(product.getProductId().getValue(), "누누", "url", 2);
        final Optional<Product> updatedProduct = stubProductRepository.findById(product.getProductId().getValue());
        assertAll(
                () -> Assertions.assertThat(result.getProductId().getValue()).isPositive(),
                () -> Assertions.assertThat(result.getProductName().getValue()).isEqualTo("누누"),
                () -> Assertions.assertThat(result.getProductImage().getValue()).isEqualTo("url"),
                () -> Assertions.assertThat(result.getProductPrice().getValue()).isEqualTo(2),
                () -> Assertions.assertThat(updatedProduct).isPresent(),
                () -> Assertions.assertThat(updatedProduct.get().getProductId().getValue()).isPositive(),
                () -> Assertions.assertThat(updatedProduct.get().getProductName().getValue()).isEqualTo("누누"),
                () -> Assertions.assertThat(updatedProduct.get().getProductImage().getValue()).isEqualTo("url"),
                () -> Assertions.assertThat(updatedProduct.get().getProductPrice().getValue()).isEqualTo(2)
        );
    }
}
