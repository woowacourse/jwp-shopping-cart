package cart.service;

import static cart.domain.ProductFixture.ODO_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import cart.domain.Product;
import cart.repository.StubProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings({"NonAsciiCharacters"})
class ProductSearchServiceTest {

    private StubProductRepository stubProductRepository;
    private ProductSearchService productSearchService;

    @BeforeEach
    void setUp() {
        stubProductRepository = new StubProductRepository();
        productSearchService = new ProductSearchService(stubProductRepository);
    }

    @Test
    void 조회_테스트() {
        stubProductRepository.save(ODO_PRODUCT);

        final List<Product> result = productSearchService.findAll();

        assertThat(result).hasSize(1);
    }
}
