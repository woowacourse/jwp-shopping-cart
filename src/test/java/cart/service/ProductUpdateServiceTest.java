package cart.service;

import static cart.domain.ProductFixture.ODO_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Product;
import cart.repository.StubProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"NonAsciiCharacters"})
class ProductUpdateServiceTest {

    private StubProductRepository stubProductRepository;
    private ProductUpdateService productUpdateService;

    @BeforeEach
    void setUp() {
        stubProductRepository = new StubProductRepository();
        productUpdateService = new ProductUpdateService(stubProductRepository);
    }

    @Test
    void 업데이트_테스트() {
        //given
        final Product product = stubProductRepository.save(ODO_PRODUCT);

        //when
        final Product result = productUpdateService.update(product.getProductId().getValue(), "누누", "url", 2);

        //then
        final Optional<Product> updatedProduct = stubProductRepository.findById(product.getProductId().getValue());
        assertAll(
                () -> assertThat(result.getProductId().getValue()).isPositive(),
                () -> assertThat(result.getProductName().getValue()).isEqualTo("누누"),
                () -> assertThat(result.getProductImage().getValue()).isEqualTo("url"),
                () -> assertThat(result.getProductPrice().getValue()).isEqualTo(2),
                () -> assertThat(updatedProduct).isPresent(),
                () -> assertThat(updatedProduct.get().getProductId().getValue()).isPositive(),
                () -> assertThat(updatedProduct.get().getProductName().getValue()).isEqualTo("누누"),
                () -> assertThat(updatedProduct.get().getProductImage().getValue()).isEqualTo("url"),
                () -> assertThat(updatedProduct.get().getProductPrice().getValue()).isEqualTo(2)
        );
    }
}
