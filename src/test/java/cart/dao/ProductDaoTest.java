package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.entity.Product;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

@JdbcTest
@Sql(scripts = {"classpath:sql/initProducts.sql"})
public class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("모든 Product 조회")
    void selectAll() {
        System.out.println("product");
        List<Product> products = productDao.selectAll();

        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("상품 저장")
    void save() {
        System.out.println("roll");
        assertDoesNotThrow(
                () -> productDao.save(
                        new Product.Builder()
                                .name("a")
                                .price(100)
                                .imageUrl("미성씨")
                                .build()
                )
        );
    }


    @Test
    @DisplayName("상품 삭제")
    void deleteById() {
        System.out.println("abc");
        assertDoesNotThrow(
                () -> productDao.deleteById(1)
        );
    }

}
