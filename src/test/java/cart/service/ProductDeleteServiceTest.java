package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import cart.domain.Product;
import cart.repository.StubProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductDeleteServiceTest {

    private StubProductRepository stubProductRepository;
    private ProductDeleteService productDeleteService;

    @BeforeEach
    void setUp() {
        this.stubProductRepository = new StubProductRepository();
        this.productDeleteService = new ProductDeleteService(stubProductRepository);
    }

    @Test
    void 제거_테스트() {
        final Product product = stubProductRepository.save(new Product("오도", "url", 1));
        final long productId = product.getProductId().getValue();
        productDeleteService.delete(productId);
        final Optional<Product> result = stubProductRepository.findById(productId);
        assertThat(result).isEmpty();
    }
}
