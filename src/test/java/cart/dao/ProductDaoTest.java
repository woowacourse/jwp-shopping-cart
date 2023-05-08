package cart.dao;

import static cart.entity.EntityFixture.ODO_ENTITY;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.Optional;

import cart.entiy.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SuppressWarnings({"NonAsciiCharacters", "SpellCheckingInspection"})
@JdbcTest
class ProductDaoTest {

    private ProductDao productDao;

    @Autowired
    private void setUp(final JdbcTemplate jdbcTemplate) {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    void 생성_테스트() {
        final ProductEntity result = productDao.insert(ODO_ENTITY);

        assertAll(
                () -> assertThat(result.getId().getValue()).isPositive(),
                () -> assertThat(result.getName()).isEqualTo("오도"),
                () -> assertThat(result.getImage()).isEqualTo("naver.com"),
                () -> assertThat(result.getPrice()).isEqualTo(1)
        );
    }

    @Nested
    class NotInsertTest {

        private long productId;

        @BeforeEach
        public void setUp() {
            productId = productDao.insert(ODO_ENTITY).getId().getValue();
        }

        @Test
        void 아이디_조회_테스트() {
            final Optional<ProductEntity> result = productDao.findById(productId);

            assertAll(
                    () -> assertThat(result).isPresent(),
                    () -> assertThat(result.get().getName()).isEqualTo("오도"),
                    () -> assertThat(result.get().getImage()).isEqualTo("naver.com"),
                    () -> assertThat(result.get().getPrice()).isEqualTo(1)
            );
        }

        @Test
        void 아이디_삭제_테스트() {
            assertAll(
                    () -> assertThatCode(() -> productDao.deleteById(productId))
                            .doesNotThrowAnyException(),
                    () -> assertThat(productDao.findById(productId)).isEmpty()
            );
        }

        @Test
        void 아이디_업데이트_테스트() {
            //given
            final ProductEntity productEntity = new ProductEntity(productId, "누누", "url", 1);
            productDao.update(productEntity);

            //when
            final Optional<ProductEntity> result = productDao.findById(productId);

            //then
            assertAll(
                    () -> assertThat(result).isPresent(),
                    () -> assertThat(result.get().getName()).isEqualTo("누누"),
                    () -> assertThat(result.get().getImage()).isEqualTo("url"),
                    () -> assertThat(result.get().getPrice()).isEqualTo(1)
            );
        }

        @Test
        void 조회_테스트() {
            final List<ProductEntity> result = productDao.findAll();
            assertThat(result).hasSize(1);
        }
    }
}
