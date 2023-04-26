package cart.persistence.dao;

import static org.assertj.core.api.AssertionsForClassTypes.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import cart.persistence.entity.ProductEntity;

@JdbcTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class JdbcProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new JdbcProductDao(jdbcTemplate);
    }

    @Test
    void save_메서드로_Product를_저장한다() {
        final ProductEntity productEntity = new ProductEntity("modi", 10000,"");

        final Long productId = productDao.save(productEntity);

        assertThat(productId).isGreaterThan(0L);
    }

    @Test
    void findAll_메서드로_저장된_Product의_목록을_불러온다() {
        final ProductEntity modi = new ProductEntity("modi", 10000, "");
        final ProductEntity jena = new ProductEntity("jena", 100000, "");
        productDao.save(modi);
        productDao.save(jena);

        final List<ProductEntity> products = productDao.findAll();

        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    void update_메서드로_저장된_Product를_수정한다() {
        final ProductEntity modi = new ProductEntity("modi", 10000, "");
        final Long productId = productDao.save(modi);
        final ProductEntity originalJena = new ProductEntity(productId,"jena", 10000, "");

        productDao.update(originalJena);
        ProductEntity jena = productDao.findByName("jena");
        assertThat(jena.getName()).isEqualTo("jena");
    }

    @Test
    void deleteByName_메서드로_저장된_Product를_삭제한다() {
        final ProductEntity modi = new ProductEntity("modi", 10000, "");
        final Long id = productDao.save(modi);

        productDao.deleteById(id);

        assertThatThrownBy(() -> productDao.findByName("modi")).isInstanceOf(EmptyResultDataAccessException.class);
    }
}
