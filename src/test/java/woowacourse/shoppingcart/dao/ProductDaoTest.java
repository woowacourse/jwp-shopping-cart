package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import woowacourse.setup.DatabaseTest;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.util.DatabaseFixture;

@SuppressWarnings("NonAsciiCharacters")
class ProductDaoTest extends DatabaseTest {

    private final ProductDao productDao;
    private final DatabaseFixture databaseFixture;

    public ProductDaoTest(NamedParameterJdbcTemplate jdbcTemplate) {
        productDao = new ProductDao(jdbcTemplate);
        databaseFixture = new DatabaseFixture(jdbcTemplate);
    }

    @Test
    void findProducts_메서드는_모든_상품_목록을_조회한다() {
        Product 호박 = new Product(1L, "호박", 1000, "호박_이미지");
        Product 고구마 = new Product(2L, "고구마", 2000, "고구마_이미지");
        Product 호박고구마 = new Product(3L, "호박고구마", 3000, "호박_고구마_이미지");
        databaseFixture.save(호박, 고구마, 호박고구마);

        List<Product> actual = productDao.findProducts();
        List<Product> expected = List.of(호박, 고구마, 호박고구마);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("findById 메서드는 id에 해당되는 데이터를 조회하여 Optional로 반환")
    @Nested
    class FindByIdTest {

        @Test
        void 존재하는_상품인_경우_값이_있는_Optional_반환() {
            Product 호박 = new Product(1L, "호박", 1000, "호박_이미지");
            databaseFixture.save(호박);

            Product actual = productDao.findById(1L).get();

            assertThat(actual).isEqualTo(호박);
        }

        @Test
        void 존재하지_않는_상품인_경우_값이_없는_Optional_반환() {
            Long 존재하지_않는_데이터의_id = 99999L;

            boolean exists = productDao.findById(존재하지_않는_데이터의_id).isPresent();

            assertThat(exists).isFalse();
        }
    }
}
