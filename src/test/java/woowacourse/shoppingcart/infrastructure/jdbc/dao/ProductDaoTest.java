package woowacourse.shoppingcart.infrastructure.jdbc.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:init.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(final DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @DisplayName("상품을 단건 조회한다.")
    @Test
    void findProductById() {
        Optional<Product> product = productDao.findProductById(1L);

        assertThat(product).isPresent();
        assertThat(product.get())
                .extracting("name", "price", "image")
                .containsExactly("우유", 3000, "http://example1.com");
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void getProducts() {
        final List<Product> products = productDao.findProducts();

        assertThat(products.size()).isEqualTo(2);
    }
}
