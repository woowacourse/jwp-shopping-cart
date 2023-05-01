package cart.product.dao;

import cart.product.domain.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
class H2ProductDaoTest {

    private final ProductDao productDao;

    @Autowired
    H2ProductDaoTest(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.productDao = new H2ProductDao(namedParameterJdbcTemplate);
    }

    @Test
    @DisplayName("product를 저장한다")
    void insert() {
        // given
        Product product = new Product("도이치킨", "image1", 1000);

        // when
        Product inserted = productDao.save(product);

        // then
        assertThat(productDao.findAll()).contains(inserted);
    }

    @Test
    @DisplayName("product를 수정한다")
    void update() {
        // given
        Product product = new Product("도이치킨", "image1", 1000);
        Product inserted = productDao.save(product);

        // when
        Product updated = new Product(inserted.getId(), "에밀치킨", "image2", 10000);
        productDao.update(updated);

        // then
        assertThat(productDao.findById(inserted.getId()).get()).isEqualTo(updated);
    }

    @Test
    @DisplayName("product를 id로 조회한다")
    void findById() {
        // given
        Product product = new Product("도이치킨", "image1", 1000);

        // when
        Product inserted = productDao.save(product);

        // then
        assertThat(productDao.findById(inserted.getId()).get()).isEqualTo(inserted);
    }
}
