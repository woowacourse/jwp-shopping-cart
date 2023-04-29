package cart.service;

import static cart.domain.ProductFixture.ODO_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.Product;
import cart.repository.StubProductRepository;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class ProductDeleteServiceTest {

    private StubProductRepository stubProductRepository;
    private ProductDeleteService productDeleteService;

    @BeforeEach
    void setUp() {
        stubProductRepository = new StubProductRepository();
        productDeleteService = new ProductDeleteService(stubProductRepository);
    }

    @Test
    void 제거_테스트() {
        //given
        final Product product = stubProductRepository.save(ODO_PRODUCT);
        final long productId = product.getProductId().getValue();

        //when
        productDeleteService.delete(productId);

        //then
        final Optional<Product> result = stubProductRepository.findById(productId);
        assertThat(result).isEmpty();
    }
}
