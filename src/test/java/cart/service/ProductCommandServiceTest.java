package cart.service;

import static cart.domain.ProductFixture.ODO_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.Product;
import cart.repository.StubProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
class ProductCommandServiceTest {

    private StubProductRepository stubProductRepository;
    private ProductCommandService productCommandService;

    @BeforeEach
    void setUp() {
        stubProductRepository = new StubProductRepository();
        productCommandService = new ProductCommandService(stubProductRepository);
    }

    @Test
    void 생성_테스트() {
        final Product result = productCommandService.create("오도", "naver.com", 1);

        final Optional<Product> product = stubProductRepository.findById(1L);
        assertAll(
                () -> assertThat(result.getProductId()).isPositive(),
                () -> assertThat(product).isPresent()
        );
    }

    @Test
    void 제거_테스트() {
        //given
        final Product product = stubProductRepository.save(ODO_PRODUCT);
        final long productId = product.getProductId();

        //when
        productCommandService.delete(productId);

        //then
        final Optional<Product> result = stubProductRepository.findById(productId);
        assertThat(result).isEmpty();
    }

    @Test
    void 업데이트_테스트() {
        //given
        final Product product = stubProductRepository.save(ODO_PRODUCT);

        //when
        final Product result = productCommandService.update(product.getProductId(), "누누", "url", 2);

        //then
        final Optional<Product> updatedProduct = stubProductRepository.findById(product.getProductId());
        assertAll(
                () -> assertThat(result.getProductId()).isPositive(),
                () -> assertThat(result.getProductNameValue()).isEqualTo("누누"),
                () -> assertThat(result.getProductImage().getValue()).isEqualTo("url"),
                () -> assertThat(result.getProductPrice().getValue()).isEqualTo(2),
                () -> assertThat(updatedProduct).isPresent(),
                () -> assertThat(updatedProduct.get().getProductId()).isPositive(),
                () -> assertThat(updatedProduct.get().getProductNameValue()).isEqualTo("누누"),
                () -> assertThat(updatedProduct.get().getProductImage().getValue()).isEqualTo("url"),
                () -> assertThat(updatedProduct.get().getProductPrice().getValue()).isEqualTo(2)
        );
    }
}
