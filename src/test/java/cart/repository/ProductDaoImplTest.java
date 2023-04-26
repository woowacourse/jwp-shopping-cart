package cart.repository;

import cart.domain.Product;
import cart.entity.ProductEntity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class ProductDaoImplTest {

    @Autowired
    JdbcTemplate jdbcTemplate;
    ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDaoImpl(jdbcTemplate);
    }

    @Test
    void 상품이_정상적으로_저장된다() {
        Product product = new Product("pizza", "url", BigDecimal.valueOf(10000));
        ProductEntity created = productDao.save(product).orElseGet(() -> null);
        assertThat(created).isNotNull();
    }

    @Test
    void 상품_데이터_정합성_검증() {
        Product product = new Product("pizza", "url", BigDecimal.valueOf(10000));
        ProductEntity created = productDao.save(product).orElseGet(() -> null);

        Assertions.assertAll(
                () -> assertThat(created).isNotNull(),
                () -> assertThat(product.getName()).isEqualTo(created.getName())
        );
    }
}
