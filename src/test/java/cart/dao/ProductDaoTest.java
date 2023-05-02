package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.entity.Product;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    @BeforeEach
    void beforeEach() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("모든 상품 조회")
    void selectAll() {
        List<Product> products = productDao.selectAll();

        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    @DisplayName("상품 저장")
    void save() {
        assertDoesNotThrow(
                () -> productDao.save(
                        new Product.Builder()
                                .name("샐러드")
                                .price(10000)
                                .imageUrl("밋엉씨")
                                .build()
                )
        );
    }

    @Test
    @DisplayName("상품 삭제")
    void deleteById() {
        assertDoesNotThrow(
                () -> productDao.deleteById(1)
        );
    }

    @Test
    @DisplayName("상품 수정")
    void updateById() {
        assertDoesNotThrow(
                () -> productDao.updateById(1, new Product.Builder()
                        .name("치킨")
                        .price(10000)
                        .imageUrl("밋엉")
                        .build())
        );
    }

//    @AfterEach
//    void afterEach() {
//        jdbcTemplate.update("TRUNCATE TABLE product");
//    }

}
