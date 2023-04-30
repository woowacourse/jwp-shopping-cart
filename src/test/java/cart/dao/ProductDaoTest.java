package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import cart.entity.Product;
import java.util.List;
import java.util.Optional;
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
    void beforeEach() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    @DisplayName("모든 상품 조회")
    void selectAll() {
        List<Product> products = productDao.selectAll();

        assertThat(products.size()).isEqualTo(3);
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
                () -> productDao.updateById(2, new Product.Builder()
                        .name("치킨")
                        .price(10000)
                        .imageUrl("밋엉")
                        .build())
        );
    }

    @Test
    @DisplayName("id로 상품을 조회한다.")
    void findById() {
        Optional<Product> productOptional = productDao.findById(2);

        Product product = productOptional.get();
        assertThat(product.getName()).isEqualTo("치킨");
    }

    @Test
    @DisplayName("없는 id로 상품을 조회하면 빈 값을 반환한다.")
    void findByNonExistId() {
        Optional<Product> productOptional = productDao.findById(1);

        assertThat(productOptional.isEmpty()).isTrue();
    }

}
