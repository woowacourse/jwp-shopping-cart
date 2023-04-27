package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@DisplayName("ProductDao 은")
class ProductDaoTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private ProductDao productDao;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    void 상품_저장() {
        // when
        final Long id = productDao.save(new Product("말링", "채채", 10000));

        // then
        assertThat(id).isNotNull();
    }

    @Test
    void 상품_조회() {
        // given
        final Long id = productDao.save(new Product("말링", "채채", 10000));

        // expected
        assertThat(productDao.findById(id)).isPresent();
    }

    @Test
    void 상품_조회_실패() {
        // expected
        assertThat(productDao.findById(1L)).isEmpty();
    }

    @Test
    void 상품_전체_조회() {
        // given
        productDao.save(new Product("말링", "채채", 10000));
        productDao.save(new Product("말링2", "채채2", 20000));

        // expected
        assertThat(productDao.findAll()).hasSize(2);
    }

    @Test
    void 상품_업데이트() {
        // given
        final Long id = productDao.save(new Product("말링", "채채", 10000));

        // when
        productDao.update(new Product(id, "수정이미지", "수정이미지", 3000));

        // then
        assertThat(productDao.findById(id).get().getName())
                .isEqualTo("수정이미지");
    }

    @Test
    void 상품_삭제() {
        // given
        final Long id = productDao.save(new Product("말링", "채채", 10000));

        // when
        productDao.deleteById(id);

        // then
        assertThat(productDao.findById(id)).isEmpty();
    }
}
