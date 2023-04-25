package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class ProductDaoImplTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setting() {
        jdbcTemplate.execute("DROP TABLE product IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE product("
            + "id SERIAL, "
            + "name VARCHAR(255), "
            + "image_url VARCHAR(255), "
            + "price INT "
            + ")");
    }

    @DisplayName("상품을 저장한다.")
    @Test
    void insert_product() {
        // given
        ProductDaoImpl productDao = new ProductDaoImpl(jdbcTemplate);
        Product product = new Product("연필", "이미지url", 1000);

        // when
        Long result = productDao.insertProduct(product);

        // then
        assertThat(result).isEqualTo(1);
    }

    @DisplayName("상품 전체를 조회한다.")
    @Test
    void find_all_product() {
        // given
        ProductDaoImpl productDao = new ProductDaoImpl(jdbcTemplate);
        Product product1 = new Product("연필", "이미지url", 1000);
        Product product2 = new Product("지우개", "이미지url", 1000);
        productDao.insertProduct(product1);
        productDao.insertProduct(product2);

        // when
        List<Product> result = productDao.findAll();

        // then
        assertThat(result.size()).isEqualTo(2);
    }

    @DisplayName("상품 아이디를 통해서 상품을 조회한다.")
    @Test
    void find_product_by_id() {
        // given
        ProductDaoImpl productDao = new ProductDaoImpl(jdbcTemplate);
        Product product1 = new Product("연필", "이미지url", 1000);
        Product product2 = new Product("지우개", "이미지url", 1000);
        Long product1Id = productDao.insertProduct(product1);
        Long product2Id = productDao.insertProduct(product2);

        // when
        Optional<Product> result = productDao.findById(product1Id);

        // then
        assertThat(result.get().getName()).isEqualTo("연필");
        assertThat(result.get().getImageUrl()).isEqualTo("이미지url");
        assertThat(result.get().getPrice()).isEqualTo(1000);
    }
}