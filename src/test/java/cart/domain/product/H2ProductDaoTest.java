package cart.domain.product;

import static cart.fixture.ProductFixture.PRODUCT1;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@SqlGroup({
        @Sql("/schema.sql"),
        @Sql("/data.sql")
})
class H2ProductDaoTest {

    private final ProductDao productDao;

    @Autowired
    H2ProductDaoTest(final JdbcTemplate jdbcTemplate) {
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
        // when
        Product updated = new Product(PRODUCT1.getId(), "에밀치킨", "image2", 10000);
        productDao.update(updated);

        // then
        assertThat(productDao.findById(PRODUCT1.getId()).get()).isEqualTo(updated);
    }

    @DisplayName("product를 id로 조회한다")
    @Test
    void findById() {
        assertThat(productDao.findById(PRODUCT1.getId()).get()).isEqualTo(PRODUCT1);
    }
}
