package woowacourse.shoppingcart.product.support.jdbc.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import javax.sql.DataSource;
import woowacourse.shoppingcart.product.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql({"classpath:schema.sql", "classpath:init.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(final DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findById() {
        final Optional<Product> product = productDao.findById(1L);
        assertThat(product).isPresent();
    }

    @DisplayName("상품 목록 조회")
    @Test
    void findAll() {
        final List<Product> products = productDao.findAll();
        assertThat(products).hasSize(4);
    }
}
