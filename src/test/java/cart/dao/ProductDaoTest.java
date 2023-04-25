package cart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.Optional;

import cart.entiy.ProductEntity;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class ProductDaoTest {

    private ProductDao productDao;

    @Autowired
    private void setUp(final JdbcTemplate jdbcTemplate) {
        productDao = new ProductDao(jdbcTemplate);
    }

    @Test
    void 생성_테스트() {
        final ProductEntity productEntity = new ProductEntity("오도", "url", 1);

        final ProductEntity result = productDao.insert(productEntity);

        assertAll(
                () -> assertThat(result.getId().getValue()).isPositive(),
                () -> assertThat(result.getName()).isEqualTo("오도"),
                () -> assertThat(result.getImage()).isEqualTo("url"),
                () -> assertThat(result.getPrice()).isEqualTo(1)
        );
    }

    @Nested
    class NotInsertTest {
        private long productId;

        @BeforeEach
        public void setUp() {
            productId = productDao.insert(new ProductEntity("오도", "url", 3)).getId().getValue();
        }

        @Test
        void 아이디_조회_테스트() {
            final Optional<ProductEntity> result = productDao.findById(productId);

            assertAll(
                    () -> assertThat(result).isPresent(),
                    () -> assertThat(result.get().getName()).isEqualTo("오도"),
                    () -> assertThat(result.get().getImage()).isEqualTo("url"),
                    () -> assertThat(result.get().getPrice()).isEqualTo(3)
            );
        }

        @Test
        void 아이디_삭제_테스트() {
            Assertions.assertThatCode(() -> productDao.deleteById(productId))
                    .doesNotThrowAnyException();
            final Optional<ProductEntity> result = productDao.findById(productId);
            assertThat(result).isEmpty();
        }

        @Test
        void 아이디_업데이트_테스트() {
            final ProductEntity productEntity = new ProductEntity(productId, "누누", "url", 1);
            productDao.update(productEntity);
            final Optional<ProductEntity> result = productDao.findById(productId);
            assertAll(
                    () -> assertThat(result).isPresent(),
                    () -> assertThat(result.get().getName()).isEqualTo("누누"),
                    () -> assertThat(result.get().getImage()).isEqualTo("url"),
                    () -> assertThat(result.get().getPrice()).isEqualTo(1)
            );
        }
    }
}
