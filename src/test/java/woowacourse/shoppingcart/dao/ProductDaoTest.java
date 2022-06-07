package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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
import woowacourse.shoppingcart.exception.NotFoundProductException;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(value = {"/schema.sql", "/data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
    }


    @DisplayName("productID를 상품을 찾으면, product를 반환한다.")
    @Test
    void findProductById_exist_productReturned() {
        // given
        Product expect = new Product(1L, "product1", 1000, "url1");

        // when
        final Product product = productDao.findProductById(1L);

        // then
        assertThat(product).isEqualTo(expect);
    }

    @DisplayName("존재하지 않은 productId를 상품을 찾으면, 예외를 발생시킨다.")
    @Test
    void findProductById_notExist_exceptionThrown() {
        // when, then

        assertThatThrownBy(() -> productDao.findProductById(20L))
                .isInstanceOf(NotFoundProductException.class);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void findProducts() {
        // given
        Product product1 = new Product(1L, "product1", 1000, "url1");
        Product product2 = new Product(2L, "product2", 2000, "url2");
        Product product3 = new Product(3L, "product3", 3000, "url3");
        Product product4 = new Product(4L, "product4", 4000, "url4");
        Product product5 = new Product(5L, "product5", 5000, "url5");
        Product product6 = new Product(6L, "product6", 6000, "url6");
        Product product7 = new Product(7L, "product7", 7000, "url7");
        Product product8 = new Product(8L, "product8", 8000, "url8");
        Product product9 = new Product(9L, "product9", 9000, "url9");
        Product product10 = new Product(10L, "product10", 10000, "url10");


        // when
        List<Product> products = productDao.findProducts();

        // then
        assertThat(products).containsExactly(product1, product2, product3, product4, product5, product6, product7,
                product8, product9, product10);
    }

    @DisplayName("product id들로 product를 조회한다.")
    @Test
    void findProductsByIds() {
        // given
        Product product1 = new Product(1L, "product1", 1000, "url1");
        Product product2 = new Product(2L, "product2", 2000, "url2");
        Product product3 = new Product(3L, "product3", 3000, "url3");

        // when
        List<Product> products = productDao.findProductsByIds(List.of(3L, 1L, 2L));

        // then
        assertThat(products).containsExactly(product3, product1, product2);
    }
}
