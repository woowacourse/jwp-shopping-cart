package cart.dao;

import cart.dao.ProductDao;
import cart.dao.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@JdbcTest
class ProductDaoTest {

    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("save() : 물품을 저장할 수 있다.")
    void test_save() throws Exception {
        //given
        final String name = "피자";
        final int price = 10000;
        final String imageUrl = "imageUrl";

        final ProductEntity productEntity = new ProductEntity(name, price, imageUrl);

        //when & then
        assertDoesNotThrow(() -> productDao.save(productEntity));
    }
}
