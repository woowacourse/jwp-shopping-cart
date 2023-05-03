package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.product.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql("classpath:test.sql")
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    @BeforeEach
    void setup() {
        this.productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    void 상품_데이터_삽입() {
        final Product product = new Product("name4", 3000, "https://image4.com");

        final long id = productDao.insert(product);

        assertThat(id).isEqualTo(4L);
    }

    @Test
    void 상품_데이터_조회() {
        final long id = 2L;

        final Product foundProduct = productDao.findById(id);

        assertAll(
                () -> assertThat(foundProduct.getName()).isEqualTo("name2"),
                () -> assertThat(foundProduct.getPrice()).isEqualTo(2000)
        );
    }

    @Test
    void 모든_상품_데이터_조회() {
        final List<Product> results = productDao.findAll();

        assertThat(results.size()).isEqualTo(3);
    }

    @Test
    void 상품_데이터_수정() {
        final long id = 2L;
        final Product newProduct = new Product(id, "new salad", 3000, "https://salad.com");

        productDao.update(newProduct);
        final Product foundProduct = productDao.findById(id);

        assertAll(
                () -> assertThat(foundProduct.getName()).isEqualTo(newProduct.getName()),
                () -> assertThat(foundProduct.getPrice()).isEqualTo(newProduct.getPrice())
        );
    }

    @Test
    void 존재하지_않는_상품_수정시_예외_발생() {
        final Product notExistProduct = new Product(3L, "커피", 2800, "https://coffee.com");

        productDao.update(notExistProduct);
    }
}
