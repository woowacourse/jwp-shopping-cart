package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.ImageUrl;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ProductName;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql({"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.productDao = new ProductDao(namedParameterJdbcTemplate);
    }

    @DisplayName("상품을 저장한다.")
    @Test
    void save() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        // when
        final Product product = productDao.save(new Product(new ProductName(name), price, new ImageUrl(imageUrl)));

        // then
        assertThat(product.getId()).isEqualTo(5L);
    }

    @DisplayName("상품의 id로 상품을 조회한다.")
    @Test
    void findProductById() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";
        final Product product = productDao.save(new Product(new ProductName(name), price, new ImageUrl(imageUrl)));

        // when
        final Optional<Product> savedProduct = productDao.findById(5L);
        final Product expectedProduct = new Product(5L, new ProductName(name), price, new ImageUrl(imageUrl));

        // then
        assert (savedProduct).isPresent();
        assertThat(savedProduct.get()).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("저장된 모든 상품을 조회한다.")
    @Test
    void getProducts() {
        // when
        final List<Product> products = productDao.findAll();

        // then
        assertThat(products).size().isEqualTo(4);
    }

    @DisplayName("상품의 id로 특정 상품을 삭제한다.")
    @Test
    void deleteProduct() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        final Product product = productDao.save(new Product(new ProductName(name), price, new ImageUrl(imageUrl)));
        final int beforeSize = productDao.findAll().size();

        // when
        productDao.delete(product.getId());

        // then
        final int afterSize = productDao.findAll().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
