package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.Fixtures.물품추가;
import static woowacourse.Fixtures.치킨;
import static woowacourse.Fixtures.피자;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.ProductResponse;

@SpringBootTest
@Sql("/init.sql")
@Transactional
class ProductServiceTest {
    @Autowired
    private ProductService productService;
    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @DisplayName("페이지의 물품을 반환한다")
    @Test
    void findProductsOfPage() {
        // given
        물품추가(jdbcTemplate, 치킨);
        물품추가(jdbcTemplate, 피자);

        // when
        List<ProductResponse> productsOfPage = productService.findProductsOfPage(1, 1);

        //then
        assertThat(productsOfPage)
                .usingRecursiveComparison()
                .isEqualTo(List.of(치킨));

    }

    @DisplayName("제품 아이디에 해당하는 제품을 반환한다.")
    @Test
    void findProductById() {
        // given
        Long 치킨아이디 = 물품추가(jdbcTemplate, 치킨);
        Long 피자아이디 = 물품추가(jdbcTemplate, 피자);

        // when
        ProductResponse product = productService.findProductById(치킨아이디);

        //then
        assertThat(product)
                .usingRecursiveComparison()
                .isEqualTo(치킨);

    }
}
