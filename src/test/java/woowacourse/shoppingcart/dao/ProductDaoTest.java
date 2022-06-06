package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.utils.Fixture.맥주;
import static woowacourse.utils.Fixture.초콜렛;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.domain.product.Product;

@JdbcTest
public class ProductDaoTest {

    private final ProductDao productDao;

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    public ProductDaoTest(DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @DisplayName("Product를 저장하면, 저장된 Product를 반환한다")
    @Test
    void save() {
        // when
        Product product = productDao.save(초콜렛);

        // then
        assertThat(product).isNotNull();
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        Product saved = productDao.save(초콜렛);
        Product expectedProduct = new Product(saved.getId(), 초콜렛.getName(), 초콜렛.getPrice(), 초콜렛.getImage());

        // when
        Product product = productDao.findProductById(saved.getId()).get();

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        Product saved1 = productDao.save(초콜렛);
        Product saved2 = productDao.save(맥주);

        // when
        List<Product> products = productDao.findProducts();

        // then
        assertThat(products).hasSize(2)
                .extracting("name")
                .containsExactly(초콜렛.getName(), 맥주.getName());
    }

    @DisplayName("싱품 삭제")
    @Test
    void deleteProduct() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";

        final Product saved = productDao.save(new Product(name, price, imageUrl));
        final int beforeSize = productDao.findProducts().size();

        // when
        productDao.delete(saved.getId());

        // then
        final int afterSize = productDao.findProducts().size();
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}
