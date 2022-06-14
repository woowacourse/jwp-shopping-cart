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
import woowacourse.shoppingcart.domain.Image;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final String name = "초콜렛";
    private final int price = 1_000;
    private final int stockQuantity = 10;
    private final String imageUrl = "www.test.com";
    private final String imageAlt = "ImageAlt";
    private final Image image = new Image(imageUrl, imageAlt);

    private final ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @DisplayName("product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // when
        final Long productId = productDao.save(new Product(name, price, stockQuantity, image));

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final Long productId = productDao.save(new Product(name, price, stockQuantity, image));
        final Product expectedProduct = new Product(productId, name, price, stockQuantity, image);

        // when
        final Product product = productDao.findById(productId);

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        final int size = 0;

        // when
        final List<Product> products = productDao.findProducts();

        // then
        assertThat(products).size().isEqualTo(size);
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final Long productId = productDao.save(new Product(name, price, stockQuantity, image));
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.deleteById(productId);

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
