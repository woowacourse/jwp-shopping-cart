package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ThumbnailImage;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;
    private final ThumbnailImageDao thumbnailImageDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
        this.thumbnailImageDao = new ThumbnailImageDao(jdbcTemplate);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final int stockQuantity = 10;
        final ThumbnailImage thumbnailImage = new ThumbnailImage("url", "alt");

        // when
        final Long productId = productDao.save(new Product(name, price, stockQuantity, thumbnailImage));

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final int stockQuantity = 10;
        final ThumbnailImage thumbnailImage = new ThumbnailImage("url", "alt");

        final Long productId = productDao.save(new Product(name, price, stockQuantity, thumbnailImage));
        thumbnailImageDao.save(thumbnailImage, productId);
        final Product expectedProduct = new Product(productId, name, price, stockQuantity, thumbnailImage);

        // when
        final Product product = productDao.getById(productId);

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {

        // given
        final int size = 0;

        // when
        final List<Product> products = productDao.getAll();

        // then
        assertThat(products).size().isEqualTo(size);
    }

    @DisplayName("상품 삭제")
    @Test
    void deleteProduct() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final int stockQuantity = 10;
        final ThumbnailImage thumbnailImage = new ThumbnailImage("url", "alt");

        final Long productId = productDao.save(new Product(name, price, stockQuantity, thumbnailImage));
        thumbnailImageDao.save(thumbnailImage, productId);
        final int beforeSize = productDao.getAll().size();

        // when
        productDao.delete(productId);

        // then
        final int afterSize = productDao.getAll().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
