package cart.persistence.dao;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import cart.persistence.dao.JdbcProductDao;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.Product;

@JdbcTest
@Sql(scripts = "classpath:schema-truncate.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
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
        final Product product = new Product("modi", 10000,"");

        final long productId = productDao.save(product);

        assertThat(productId).isGreaterThan(0L);
    }

    @Test
    void findAll_메서드로_저장된_Product의_목록을_불러온다() {
        final Product modi = new Product("modi", 10000, "");
        final Product jena = new Product("jena", 100000, "");
        productDao.save(modi);
        productDao.save(jena);

        final List<Product> products = productDao.findAll();

        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    void update_메서드로_저장된_Product를_수정한다() {
        final Product modi = new Product("modi", 10000, "");
        final Long productId = productDao.save(modi);
        final Product originalJena = new Product(productId,"jena", 10000, "");

        productDao.update(originalJena);
        Product jena = productDao.findByName("jena");
        assertThat(jena.getName()).isEqualTo("jena");
    }

    @Test
    void deleteByName_메서드로_저장된_Product를_삭제한다() {
        final Product modi = new Product("modi", 10000, "");
        final long id = productDao.save(modi);

        productDao.deleteById(id);

        assertThatThrownBy(() -> productDao.findByName("modi")).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void existsById_메서드로_주어진_product_id에_해당하는_상품이_있는지_확인한다() {
        final Product product = new Product("modi", 10000, "");
        final long id = productDao.save(product);

        assertTrue(productDao.existsById(id));
    }
}
