package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class H2ProductDaoTest {

    private final JdbcTemplate jdbcTemplate;
    private ProductDao productDao;

    @Autowired
    H2ProductDaoTest(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.productDao = new H2ProductDao(jdbcTemplate);
    }

    @DisplayName("product를 저장한다")
    @Test
    void insert() {
        // given
        Product product = new Product("도이치킨", "image1", 1000);

        // when
        Product inserted = productDao.insert(product);

        // then
        assertThat(productDao.findAll()).contains(inserted);
    }

    @DisplayName("product를 수정한다")
    @Test
    void update() {
        // given
        Product product = new Product("도이치킨", "image1", 1000);
        Product inserted = productDao.insert(product);

        // when
        Product updated = new Product(inserted.getId(), "에밀치킨", "image2", 10000);
        productDao.update(updated);

        // then
        assertThat(productDao.findById(inserted.getId()).get()).isEqualTo(updated);
    }

    @DisplayName("product를 id로 조회한다")
    @Test
    void findById() {
        // given
        Product product = new Product("도이치킨", "image1", 1000);

        // when
        Product inserted = productDao.insert(product);

        // then
        assertThat(productDao.findById(inserted.getId()).get()).isEqualTo(inserted);
    }
}
