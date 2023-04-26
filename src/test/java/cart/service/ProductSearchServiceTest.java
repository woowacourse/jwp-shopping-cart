package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import cart.domain.Product;
import cart.repository.StubProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProductSearchServiceTest {

    private StubProductRepository stubProductRepository;
    private ProductSearchService productSearchService;

    @BeforeEach
    void setUp() {
        this.stubProductRepository = new StubProductRepository();
        this.productSearchService = new ProductSearchService(stubProductRepository);
    }

    @Test
    void 조회_테스트() {
        stubProductRepository.save(new Product("오도", "naver.com", 1));
        final List<Product> result = productSearchService.find();
        assertThat(result).hasSize(1);
    }
}
