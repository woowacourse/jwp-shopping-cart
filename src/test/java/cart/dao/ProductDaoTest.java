package cart.dao;

import cart.dao.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

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
        final ProductEntity productEntity =
                new ProductEntity.Builder()
                        .name("치킨")
                        .price(10_000)
                        .image("치킨 이미지 주소")
                        .build();

        // when
        final Long id = productDao.insert(productEntity);

        // then
        assertThat(id).isNotNull();
    }

    @Test
    void 입력한_id를_갖는_데이터를_수정한다() {
        // given
        final Long id = productDao.insert(
                new ProductEntity.Builder()
                        .name("돈까스")
                        .price(10_000)
                        .image("돈까스 이미지 주소")
                        .build()
        );

        // when
        final int updatedRows = productDao.update(
                new ProductEntity.Builder()
                        .id(id)
                        .name("치킨")
                        .price(1_000)
                        .image("치킨 이미지 주소")
                        .build()
        );

        // then
        assertSoftly(softly -> {
            softly.assertThat(updatedRows).isEqualTo(1);
            ProductEntity productEntity = productDao.findById(id).get();
            softly.assertThat(productEntity.getName()).isEqualTo("치킨");
            softly.assertThat(productEntity.getPrice()).isEqualTo(1_000);
            softly.assertThat(productEntity.getImage()).isEqualTo("치킨 이미지 주소");
        });
    }

    @Test
    void 등록되지_않은_데이터를_수정할_수_없다() {
        // when
        final int updatedRows = productDao.update(
                new ProductEntity.Builder()
                        .id(99999L)
                        .name("치킨")
                        .price(1_000)
                        .image("치킨 이미지 주소")
                        .build()
        );

        // then
        assertThat(updatedRows).isZero();
    }

    @Test
    void 입력한_id를_가진_데이터를_찾는다() {
        // given
        final Long id = productDao.insert(
                new ProductEntity.Builder()
                        .name("돈까스")
                        .price(10_000)
                        .image("돈까스 이미지 주소")
                        .build()
        );

        // when
        final ProductEntity productEntity = productDao.findById(id).get();

        // then
        assertSoftly(softly -> {
            softly.assertThat(productEntity.getId()).isEqualTo(id);
            softly.assertThat(productEntity.getName()).isEqualTo("돈까스");
            softly.assertThat(productEntity.getPrice()).isEqualTo(10_000);
            softly.assertThat(productEntity.getImage()).isEqualTo("돈까스 이미지 주소");
        });
    }

    @Test
    void 입력한_id를_가진_데이터를_삭제한다() {
        // given
        final Long id = productDao.insert(
                new ProductEntity.Builder()
                        .name("돈까스")
                        .price(10_000)
                        .image("돈까스 이미지 주소")
                        .build()
        );

        // when
        productDao.delete(id);

        // then
        assertThatThrownBy(() -> productDao.findById(id).get())
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void 모든_데이터를_조회한다() {
        // given
        productDao.insert(
                new ProductEntity.Builder()
                        .name("치킨")
                        .price(1_000)
                        .image("치킨 이미지 주소")
                        .build()
        );

        // when
        final List<ProductEntity> productEntities = productDao.selectAll();

        // then
        assertThat(productEntities).hasSize(1);
    }
}
