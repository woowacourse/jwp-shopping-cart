package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.entity.Product;
import cart.entity.Product.Builder;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
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
    @DisplayName("상품을 데이터베이스에 저장한다.")
    void save() {
        int productId = productDao.save(
                new Builder()
                        .name("샐러드")
                        .price(10000)
                        .imageUrl("밋엉씨")
                        .build()
        );

        Product product = productDao.findById(productId).get();

        assertThat(product.getName()).isEqualTo("샐러드");
    }

    @Test
    @DisplayName("상품을 데이터베이스에서 삭제한다.")
    void deleteById() {
        productDao.deleteById(2);

        List<Product> products = productDao.selectAll();

        assertThat(products).hasSize(2);
    }

    @Test
    @DisplayName("상품의 정보를 데이터베이스에서 수정한다.")
    void updateById() {
        productDao.updateById(2, new Product.Builder()
                .name("치킨")
                .price(10000)
                .imageUrl("밋엉")
                .build());

        Product product = productDao.findById(2).get();

        assertThat(product.getName()).isEqualTo("치킨");
    }

    @Test
    @DisplayName("데이터베이스에서 id로 상품을 조회한다.")
    void findById() {
        Optional<Product> productOptional = productDao.findById(2);

        Product product = productOptional.get();

        assertThat(product.getName()).isEqualTo("치킨");
    }

    @Test
    @DisplayName("데이터베이스에서 없는 id로 상품을 조회하면 빈 값을 반환한다.")
    void findByNonExistId() {
        Optional<Product> productOptional = productDao.findById(1);

        assertThat(productOptional.isEmpty()).isTrue();
    }

}
