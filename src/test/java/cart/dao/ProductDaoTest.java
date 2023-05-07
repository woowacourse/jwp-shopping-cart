package cart.dao;

import cart.domain.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.SoftAssertions.assertSoftly;

@DisplayNameGeneration(ReplaceUnderscores.class)
@SuppressWarnings("NonAsciiCharacters")
@JdbcTest
@Sql("classpath:data-test.sql")
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
        final ProductEntity productEntity = new ProductEntity("치킨", 1_000, "치킨 이미지 주소");

        // when
        final Long id = productDao.insert(productEntity);

        // then
        assertThat(id).isNotNull();
    }

    @Test
    void 입력한_id를_갖는_데이터를_수정한다() {
        // given
        final Long id = productDao.insert(new ProductEntity("돈까스", 10_000, "돈까스 이미지 주소"));

        // when
        final int updatedRows = productDao.update(id, new ProductEntity("치킨", 1_000, "치킨 이미지 주소"));

        // then
        assertSoftly(softly -> {
            softly.assertThat(updatedRows).isEqualTo(1);
            ProductEntity productEntity = productDao.findById(id);
            softly.assertThat(productEntity.getName()).isEqualTo("치킨");
            softly.assertThat(productEntity.getPrice()).isEqualTo(1_000);
            softly.assertThat(productEntity.getImage()).isEqualTo("치킨 이미지 주소");
        });
    }

    @Test
    void 등록되지_않은_데이터를_수정하면_예외를_던진다() {
        //given
        final Long id = 99999L;

        //expect
        assertThatThrownBy(() -> productDao.update(id, new ProductEntity("치킨", 1_000, "치킨 이미지 주소")))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("접근하려는 데이터가 존재하지 않습니다.");
    }

    @Test
    void 입력한_id를_가진_데이터를_찾는다() {
        // given
        final Long id = productDao.insert(new ProductEntity("돈까스", 10_000, "돈까스 이미지 주소"));

        // when
        final ProductEntity productEntity = productDao.findById(id);

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
        final Long id = productDao.insert(new ProductEntity("돈까스", 10_000, "돈까스 이미지 주소"));

        // when
        productDao.delete(id);

        // then
        assertThatThrownBy(() -> productDao.findById(id))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    void 등록되지_않은_데이터를_삭제하면_예외를_던진다() {
        //given
        final Long id = 99999L;

        //expect
        assertThatThrownBy(() -> productDao.delete(id))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("접근하려는 데이터가 존재하지 않습니다.");
    }

    @Test
    void 모든_데이터를_조회한다() {
        // given
        productDao.insert(new ProductEntity("치킨", 1_000, "치킨 이미지 주소"));

        // when
        final List<ProductEntity> productEntities = productDao.findAll();

        // then
        assertThat(productEntities).hasSize(1);
    }
}
