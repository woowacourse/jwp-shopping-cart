package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import cart.domain.product.Product;
import cart.repository.StubProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
class ProductCreateServiceTest {

    private StubProductRepository stubProductRepository;
    private ProductCreateService productCreateService;

    @BeforeEach
    void setUp() {
        stubProductRepository = new StubProductRepository();
        productCreateService = new ProductCreateService(stubProductRepository);
    }

    @Test
    void 생성_테스트() {
        final Product result = productCreateService.create("오도", "naver.com", 1);

        final Optional<Product> product = stubProductRepository.findById(1L);
        assertAll(
                () -> assertThat(result.getProductId().getValue()).isPositive(),
                () -> assertThat(product).isPresent()
        );
    }
}
