package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import woowacourse.common.exception.NotFoundException;
import woowacourse.setup.DatabaseTest;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.util.DatabaseFixture;

@SuppressWarnings("NonAsciiCharacters")
class ProductServiceTest extends DatabaseTest {

    private final ProductService productService;
    private final DatabaseFixture databaseFixture;

    public ProductServiceTest(NamedParameterJdbcTemplate jdbcTemplate) {
        productService = new ProductService(new ProductDao(jdbcTemplate));
        databaseFixture = new DatabaseFixture(jdbcTemplate);
    }

    @Test
    void findProducts_메서드는_모든_상품들을_id의_역순으로_정렬하여_조회한다() {
        Product 호박 = new Product(1L, "호박", 1000, "호박_이미지");
        Product 고구마 = new Product(2L, "고구마", 2000, "고구마_이미지");
        Product 호박고구마 = new Product(3L, "호박고구마", 3000, "호박_고구마_이미지");
        databaseFixture.save(호박, 고구마, 호박고구마);

        List<Product> actual = productService.findProducts();
        List<Product> expected = List.of(호박고구마, 고구마, 호박);

        assertThat(actual).isEqualTo(expected);
    }

    @DisplayName("findProduct 메서드는 id에 해당되는 데이터를 조회")
    @Nested
    class FindProductTest {

        @Test
        void 존재하는_상품인_경우_그대로_반환() {
            Product 호박 = new Product(1L, "호박", 1000, "호박_이미지");
            databaseFixture.save(호박);

            Product actual = productService.findProduct(1L);

            assertThat(actual).isEqualTo(호박);
        }

        @Test
        void 존재하지_않는_상품인_경우_예외발생() {
            Long 존재하지_않는_데이터의_id = 99999L;

            assertThatThrownBy(() -> productService.findProduct(존재하지_않는_데이터의_id))
                    .isInstanceOf(NotFoundException.class);
        }
    }
}
