package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.ProductInsertUtil;
import woowacourse.shoppingcart.domain.Product;

@JdbcTest
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;
    private final ProductInsertUtil productInsertUtil;

    public ProductDaoTest(DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
        this.productInsertUtil = new ProductInsertUtil(dataSource);
    }

    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById() {
        // given
        final String name = "초콜렛";
        final int price = 1_000;
        final String imageUrl = "www.test.com";
        final Long productId = productInsertUtil.insert("초콜렛", 1_000, "www.test.com");
        final Product expectedProduct = new Product(productId, name, price, imageUrl);

        // when
        final Product product = productDao.findById(productId);

        // then
        assertThat(product).usingRecursiveComparison().isEqualTo(expectedProduct);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        productInsertUtil.insert("초콜렛", 1_000, "www.test1.com");
        productInsertUtil.insert("치킨", 20_000, "www.test2.com");

        // when
        final List<Product> products = productDao.findAll();

        // then
        assertThat(products).size().isEqualTo(2);
    }
}
