package cart.service;

import static cart.domain.product.ProductFixture.ODO_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.product.Product;
import cart.repository.product.StubProductRepository;
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
                () -> assertThat(result.getProductId().getValue()).isPositive(),
                () -> assertThat(product).isPresent()
        );
    }

    @Test
    void 제거_테스트() {
        //given
        final Product product = stubProductRepository.save(ODO_PRODUCT);
        final long productId = product.getProductId().getValue();

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
        final Product result = productCommandService.update(
                product.getProductId().getValue(),
                ODO_PRODUCT.getProductName(),
                ODO_PRODUCT.getProductImage().getValue(),
                ODO_PRODUCT.getProductPrice().getValue());

        //then
        final Product expect = new Product(product.getProductId().getValue(), product);
        final Optional<Product> updatedProduct = stubProductRepository.findById(product.getProductId().getValue());
        assertAll(
                () -> assertThat(result).usingRecursiveComparison().isEqualTo(expect),
                () -> assertThat(updatedProduct).isPresent(),
                () -> assertThat(updatedProduct.get()).usingRecursiveComparison().isEqualTo(expect)
        );
    }
}
