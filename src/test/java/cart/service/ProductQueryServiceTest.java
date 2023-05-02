package cart.service;

import static cart.domain.ProductFixture.ODO_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.Product;
import cart.repository.StubProductRepository;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"NonAsciiCharacters"})
class ProductQueryServiceTest {

    private StubProductRepository stubProductRepository;
    private ProductQueryService productSearchService;

    @BeforeEach
    void setUp() {
        stubProductRepository = new StubProductRepository();
        productSearchService = new ProductQueryService(stubProductRepository);
    }

    @Test
    void 조회_테스트() {
        stubProductRepository.save(ODO_PRODUCT);

        final List<Product> result = productSearchService.find();

        assertThat(result).hasSize(1);
    }
}
