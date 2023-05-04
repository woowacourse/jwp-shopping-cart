package cart.repository.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.repository.entity.ProductEntity;
import java.util.List;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@JdbcTest
public class JdbcProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new JdbcProductDao(jdbcTemplate);
    }

    @Test
    void 엔티티를_저장하고_조회한다() {
        final ProductEntity firstProductEntity = new ProductEntity("kokodak", "localhost:8080/test", 1000);
        final ProductEntity secondProductEntity = new ProductEntity("hardy", "localhost:8080/test", 1000);
        productDao.save(firstProductEntity);
        productDao.save(secondProductEntity);

        final List<ProductEntity> products = productDao.findAll();

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions.assertThat(products.size()).isEqualTo(2);
        softAssertions.assertThat(products.get(0).getName()).isEqualTo(firstProductEntity.getName());
        softAssertions.assertThat(products.get(1).getName()).isEqualTo(secondProductEntity.getName());
        softAssertions.assertAll();
    }

    @Test
    void 엔티티를_업데이트한다() {
        final ProductEntity productEntity = new ProductEntity("kokodak", "localhost:8080/test", 1000);
        productDao.save(productEntity);
        final ProductEntity updatedProductEntity = new ProductEntity(1L, "hardy", "localhost:8080/test", 2000);
        productDao.update(updatedProductEntity);

        final List<ProductEntity> products = productDao.findAll();

        assertThat(products.get(0).getName()).isEqualTo(updatedProductEntity.getName());
    }

    @Test
    void 엔티티를_삭제한다() {
        final ProductEntity productEntity = new ProductEntity("kokodak", "localhost:8080/test", 1000);
        productDao.save(productEntity);

        productDao.delete(1L);

        assertThat(productDao.findAll()).isEmpty();
    }
}
