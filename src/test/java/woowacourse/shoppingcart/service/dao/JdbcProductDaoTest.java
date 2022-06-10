package woowacourse.shoppingcart.service.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.JdbcProductDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.entity.ProductEntity;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class JdbcProductDaoTest {
    private final ProductDao productDao;

    public JdbcProductDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.productDao = new JdbcProductDao(jdbcTemplate, dataSource);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given
        final String name = "초콜렛";
        final String description = "초콜렛 입니다.";
        final int price = 1_000;
        final int stock = 10;
        final String imageUrl = "www.test.com";

        // when
        final Long productId = productDao.save(new Product(name, description, price, stock, imageUrl));

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final String name = "초콜렛";
        final String description = "초콜렛 입니다.";
        final int price = 1_000;
        final int stock = 10;
        final String imageUrl = "www.test.com";
        final Long productId = productDao.save(new Product(name, description, price, stock, imageUrl));
        final ProductEntity expectedProduct = new ProductEntity(productId, name, description, price, stock, imageUrl);

        // when
        final ProductEntity product = productDao.findById(productId);

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {

        // given
        final int size = 0;

        // when
        final List<ProductEntity> products = productDao.findAll();

        // then
        assertThat(products).size().isEqualTo(size);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final String name = "초콜렛";
        final String description = "초콜렛 입니다.";
        final int price = 1_000;
        final int stock = 10;
        final String imageUrl = "www.test.com";

        final Long productId = productDao.save(new Product(name, description, price, stock, imageUrl));
        final int beforeSize = productDao.findAll().size();

        // when
        productDao.delete(productId);

        // then
        final int afterSize = productDao.findAll().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
