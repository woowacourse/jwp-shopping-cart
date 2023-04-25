package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.entity.ProductEntity;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        this.productDao = new ProductDao(jdbcTemplate);
        jdbcTemplate.update("INSERT INTO product (name, img_url, price) VALUES (?, ?, ?)", "CuteSeonghaDoll", "https://avatars.githubusercontent.com/u/95729738?v=4", 25000);
    }

    @DisplayName("전체 상품을 조회한다.")
    @Test
    void findAll() {
        //when
        List<ProductEntity> products = productDao.findAll();
        //then
        assertThat(products.size()).isEqualTo(1);
    }
}