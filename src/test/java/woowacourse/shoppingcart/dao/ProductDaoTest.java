package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.product.ImageUrl;
import woowacourse.shoppingcart.domain.product.Name;
import woowacourse.shoppingcart.domain.product.Price;
import woowacourse.shoppingcart.domain.product.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("Product를 저장한다.")
    @Test
    void save() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        // when
        final Product savedProduct = productDao.save(
                new Product(new Name(name), new Price(price), new ImageUrl(imageUrl)));

        // then
        assertAll(
                () -> assertThat(savedProduct.getId()).isEqualTo(1L),
                () -> assertThat(savedProduct.getName()).isEqualTo("초콜렛"),
                () -> assertThat(savedProduct.getPrice()).isEqualTo(1_000),
                () -> assertThat(savedProduct.getImageUrl()).isEqualTo("www.test.com")
        );
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";
        final Product savedProduct = productDao.save(
                new Product(new Name(name), new Price(price), new ImageUrl(imageUrl)));

        // when
        final Optional<Product> product = productDao.findProductById(savedProduct.getId());
        assert (product.isPresent());

        // then
        Product foundProduct = product.get();
        assertThat(savedProduct).usingRecursiveComparison().isEqualTo(foundProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {

        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";
        productDao.save(new Product(new Name(name), new Price(price), new ImageUrl(imageUrl)));

        // when
        final List<Product> products = productDao.findProducts();

        // then
        assertThat(products).size().isEqualTo(1);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        final Product product = productDao.save(new Product(new Name(name), new Price(price), new ImageUrl(imageUrl)));
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.delete(product.getId());

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
