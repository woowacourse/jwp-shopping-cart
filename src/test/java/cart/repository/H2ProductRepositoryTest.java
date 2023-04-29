package cart.repository;

import static cart.domain.product.ProductFixture.ODO_PRODUCT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("SpellCheckingInspection")
@JdbcTest
class H2ProductRepositoryTest {

    private H2ProductRepository h2ProductRepository;

    @Autowired
    private void setUp(final JdbcTemplate jdbcTemplate) {
        h2ProductRepository = new H2ProductRepository(new ProductDao(jdbcTemplate));
    }

    @Test
    void save() {
        final Product result = h2ProductRepository.save(ODO_PRODUCT);

        assertThat(result.getProductId().getValue()).isPositive();
    }

    @Nested
    class NotSaveTest {

        private long productId;

        @BeforeEach
        void setUp() {
            productId = h2ProductRepository.save(ODO_PRODUCT).getProductId().getValue();
        }

        @Test
        void update() {
            final Product product = new Product(productId, "누누", "newUrl", 3);
            final Product result = h2ProductRepository.update(product);

            assertAll(
                    () -> assertThat(result.getProductId().getValue()).isEqualTo(productId),
                    () -> assertThat(result.getProductName().getValue()).isEqualTo("누누"),
                    () -> assertThat(result.getProductImage().getValue()).isEqualTo("newUrl"),
                    () -> assertThat(result.getProductPrice().getValue()).isEqualTo(3)
            );
        }

        @Test
        void findAll() {
            final List<Product> result = h2ProductRepository.findAll();
            assertThat(result).hasSize(1);
        }

        @Test
        void findById() {
            final Optional<Product> result = h2ProductRepository.findById(productId);

            assertAll(
                    () -> assertThat(result).isPresent(),
                    () -> assertThat(result.get().getProductId().getValue()).isEqualTo(productId),
                    () -> assertThat(result.get().getProductName().getValue()).isEqualTo("오도"),
                    () -> assertThat(result.get().getProductImage().getValue()).isEqualTo("naver.com"),
                    () -> assertThat(result.get().getProductPrice().getValue()).isEqualTo(1)
            );
        }

        @Test
        void deleteById() {
            h2ProductRepository.deleteById(productId);

            final Optional<Product> result = h2ProductRepository.findById(productId);

            assertThat(result).isEmpty();
        }
    }
}
