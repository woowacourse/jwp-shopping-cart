package cart.repository;

import cart.domain.Product;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ProductRepositoryImplTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepositoryImpl(jdbcTemplate);
    }

    @Test
    void 상품이_정상적으로_저장된다() {
        Product product = new Product("pizza", "url", 10000);
        ProductEntity created = productRepository.save(product);
        assertThat(created).isNotNull();
    }

    @Test
    void 상품_데이터_정합성_검증() {
        Product product = new Product("pizza", "url", 10000);
        ProductEntity created = productRepository.save(product);
        assertThat(product.getName()).isEqualTo(created.getName());
    }
}
