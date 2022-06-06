package woowacourse.shoppingcart.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductDaoTest {

    private final ProductDao productDao;

    public ProductDaoTest(DataSource dataSource) {
        this.productDao = new ProductDao(dataSource);
    }

    @DisplayName("Product를 저장하면, id를 반환한다.")
    @Test
    void save() {
        // given
        final String name = "치킨";
        final int price = 20_000;
        final String imageUrl = "http://chicken.test.com";

        // when
        final Long productId = productDao.save(new Product(name, price, imageUrl));

        // then
        assertThat(productId).isEqualTo(1L);
    }

    @DisplayName("상품 목록 조회")
    @Test
    void getProducts() {
        // given
        productDao.save(new Product("치킨", 20_000, "http://chicken.test.com"));
        productDao.save(new Product("피자", 30_000, "http://pizza.test.com"));
        // when
        final List<Product> products = productDao.findAll();
        // then
        assertThat(products).usingRecursiveComparison()
                .isEqualTo(List.of(
                        new Product(1L, "치킨", 20_000, "http://chicken.test.com"),
                        new Product(2L, "피자", 30_000, "http://pizza.test.com")
                ));
    }
}
