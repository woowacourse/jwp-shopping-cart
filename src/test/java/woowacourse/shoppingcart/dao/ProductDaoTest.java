package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Page;
import woowacourse.shoppingcart.domain.Product;
import java.util.List;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private static final Page PAGE = Page.of(1, 10);
    private final ProductDao productDao;

    public ProductDaoTest(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.productDao = new ProductDao(namedParameterJdbcTemplate);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        // when
        final Long productId = productDao.save(new Product(name, price, imageUrl));

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";
        final Long productId = productDao.save(new Product(name, price, imageUrl));
        final Product expectedProduct = new Product(productId, name, price, imageUrl);

        // when
        final Product product = productDao.findProductById(productId).get();

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        final int size = 0;

        // when
        final List<Product> products = productDao.findProducts(PAGE);

        // then
        assertThat(products).size().isEqualTo(size);
    }

    @DisplayName("정삭적으로 페이징이 적용되는지 확인한다.")
    @Test
    void getProductsByPage() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        productDao.save(new Product(name, price, imageUrl));
        productDao.save(new Product(name, price, imageUrl));
        productDao.save(new Product(name, price, imageUrl));

        // when
        final List<Product> firstPageProducts = productDao.findProducts(Page.of(1, 2));
        final List<Product> secondPageProducts = productDao.findProducts(Page.of(2, 2));

        // then
        assertAll(
                () -> assertThat(firstPageProducts.size()).isEqualTo(2),
                () -> assertThat(secondPageProducts.size()).isEqualTo(1)
        );
    }

    @DisplayName("상품 삭제")
    @Test
    void deleteProduct() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        final Long productId = productDao.save(new Product(name, price, imageUrl));
        final int beforeSize = productDao.findProducts(PAGE).size();

        // when
        productDao.delete(productId);

        // then
        final int afterSize = productDao.findProducts(PAGE).size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
