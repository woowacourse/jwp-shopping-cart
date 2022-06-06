package woowacourse.shoppingcart.dao;

import javax.sql.DataSource;
import org.assertj.core.groups.Tuple;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate, DataSource dataSource) {
        this.productDao = new ProductDao(jdbcTemplate, dataSource);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given
        Product product = new Product("초콜렛", 1_000, "www.test.com");

        // when
        final Long productId = productDao.save(product);

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID로 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        Product product = new Product("초콜렛", 1_000, "www.test.com");
        final Long productId = productDao.save(product);

        final Product expectedProduct =
                new Product(productId, product.getName(), product.getPrice(), product.getImageUrl());

        // when
        final Product actualProduct = productDao.findProductById(productId);

        // then
        assertThat(actualProduct).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        productDao.save(new Product("초콜렛", 1_000, "www.test.com"));

        // when
        final List<Product> products = productDao.findProducts();

        // then
        assertThat(products).hasSize(1)
                .extracting("name", "price", "imageUrl")
                .containsExactly(Tuple.tuple("초콜렛", 1_000, "www.test.com"));
    }

    @DisplayName("상품 삭제")
    @Test
    void deleteProduct() {
        // given
        Product product = new Product("초콜렛", 1_000, "www.test.com");
        final Long productId = productDao.save(product);

        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.delete(productId);

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
