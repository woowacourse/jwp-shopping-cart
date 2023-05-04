package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

import cart.domain.Product;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
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
        final Product product = new Product("치킨", 1_000, "치킨 이미지 주소");

        // when
        final Long id = productDao.insert(product);

        // then
        assertThat(id).isNotNull();
    }

    @Test
    void 입력한_id를_갖는_데이터를_수정한다() {
        // given
        final Long id = productDao.insert(new Product("돈까스", 10_000, "돈까스 이미지 주소"));

        // when
        final int updatedRows = productDao.update(id, new Product("치킨", 1_000, "치킨 이미지 주소"));

        // then
        assertSoftly(softly -> {
            softly.assertThat(updatedRows).isEqualTo(1);
            Product product = productDao.findById(id).get();
            softly.assertThat(product.getName()).isEqualTo("치킨");
            softly.assertThat(product.getPrice()).isEqualTo(1_000);
            softly.assertThat(product.getImage()).isEqualTo("치킨 이미지 주소");
        });
    }

    @Test
    void 등록되지_않은_데이터를_수정할_수_없다() {
        // when
        final int updatedRows = productDao.update(99999L, new Product("치킨", 1_000, "치킨 이미지 주소"));

        // then
        assertThat(updatedRows).isZero();
    }

    @Test
    void 입력한_id를_가진_데이터를_찾는다() {
        // given
        final Long id = productDao.insert(new Product("돈까스", 10_000, "돈까스 이미지 주소"));

        // when
        Optional<Product> productOptional = productDao.findById(id);

        // then
        assertSoftly(softly -> {
            Product product = productOptional.get();
            softly.assertThat(product.getId()).isEqualTo(id);
            softly.assertThat(product.getName()).isEqualTo("돈까스");
            softly.assertThat(product.getPrice()).isEqualTo(10_000);
            softly.assertThat(product.getImage()).isEqualTo("돈까스 이미지 주소");
        });
    }

    @Test
    void 입력한_id를_가진_데이터를_삭제한다() {
        // given
        final Long id = productDao.insert(new Product("돈까스", 10_000, "돈까스 이미지 주소"));

        // when
        productDao.delete(id);

        // then
        assertThat(productDao.findById(id)).isEmpty();
    }

    @Test
    void 모든_데이터를_조회한다() {
        // given
        productDao.insert(new Product("치킨", 1_000, "치킨 이미지 주소"));

        // when
        final Optional<List<Product>> productsOptional = productDao.findAll();

        // then
        assertSoftly(softly -> {
            List<Product> productEntities = productsOptional.get();
            softly.assertThat(productEntities).hasSize(1);
        });
    }
}
