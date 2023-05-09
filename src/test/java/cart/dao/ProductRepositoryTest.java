package cart.dao;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@Sql({"/schema.sql", "/data.sql", "/cart_data.sql"})
@JdbcTest
class ProductRepositoryTest {

    private ProductRepository productRepository;
    private Long productId;

    @Autowired
    private JdbcTemplate jdbcTemplate;
    CartDao mySQLCartDao;
    ProductDao mySQLProductDao;

    @BeforeEach
    void setUp() {
        mySQLCartDao = new MySQLCartDao(jdbcTemplate);
        mySQLProductDao = new MySQLProductDao(jdbcTemplate);
        productRepository = new ProductRepository(mySQLCartDao, mySQLProductDao);
        productId = 1L;
    }

    @Test
    @DisplayName("cart에 특정 제품 데이터를 포함한 데이터가 존재할 때 remove()를 호출하면 cart 테이블에서 해당 데이터들이 먼저 제거된다.")
    void remove() {
        //given, when
        boolean before = mySQLCartDao.isExistEntity(1L, productId);
        productRepository.remove(productId);
        boolean after = mySQLCartDao.isExistEntity(1L, productId);

        //then
        assertAll(
            () -> assertThat(before).isTrue(),
            () -> assertThat(after).isFalse()
        );
    }
}