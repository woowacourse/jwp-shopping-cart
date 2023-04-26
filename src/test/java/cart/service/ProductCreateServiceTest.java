package cart.service;

import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import cart.domain.Product;
import cart.repository.StubProductRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductCreateServiceTest {

    private StubProductRepository stubProductRepository;
    private ProductCreateService productCreateService;

    @BeforeEach
    void setUp() {
        this.stubProductRepository = new StubProductRepository();
        this.productCreateService = new ProductCreateService(stubProductRepository);
    }

    @Test
    void 생성_테스트() {
        final Product result = productCreateService.create("오도", "naver.com", 1);
        final Optional<Product> product = stubProductRepository.findById(1L);

        assertAll(
                () -> Assertions.assertThat(result.getProductId().getValue()).isPositive(),
                () -> Assertions.assertThat(product).isPresent()
        );
    }
}
