package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cart.dao.entity.ProductEntity;
import cart.domain.Product;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
class ProductDaoTest {

    private ProductDao productDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    void 데이터를_추가한다() {
        // given
        final Product product = new Product("치킨", 1000, "치킨 이미지 주소");

        // when
        Long id = productDao.insert(product);

        // then
        assertThat(id).isNotNull();
    }

    @Test
    void 데이터를_수정한다() {
        // given
        final Long id = productDao.insert(new Product("돈까스", 10000, "돈까스 이미지 주소"));

        // when
        productDao.update(new Product("치킨", 1000, "치킨 이미지 주소"), id);

        // then
        assertSoftly(softly -> {
            ProductEntity productEntity = productDao.findById(id);
            softly.assertThat(productEntity.getName()).isEqualTo("치킨");
            softly.assertThat(productEntity.getPrice()).isEqualTo(1000);
            softly.assertThat(productEntity.getImage()).isEqualTo("치킨 이미지 주소");
        });
    }

    @Test
    void id로_값을_찾는다() {
        // given
        final Long id = productDao.insert(new Product("돈까스", 10000, "돈까스 이미지 주소"));

        // when
        ProductEntity productEntity = productDao.findById(id);

        // then
        assertSoftly(softly -> {
            softly.assertThat(productEntity.getId()).isEqualTo(id);
            softly.assertThat(productEntity.getName()).isEqualTo("돈까스");
            softly.assertThat(productEntity.getPrice()).isEqualTo(10000);
            softly.assertThat(productEntity.getImage()).isEqualTo("돈까스 이미지 주소");
        });
    }

    @Test
    void 데이터를_삭제한다() {
        // given
        final Long id = productDao.insert(new Product("돈까스", 10000, "돈까스 이미지 주소"));

        // when
        productDao.delete(id);

        // then
        assertThatThrownBy(() -> productDao.findById(id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void 데이터를_모두_읽는다() {
        // given
        productDao.insert(new Product("치킨", 1000, "치킨 이미지 주소"));

        // when
        List<ProductEntity> productEntities = productDao.selectAll();

        // then
        assertThat(productEntities).hasSize(1);
    }
}
