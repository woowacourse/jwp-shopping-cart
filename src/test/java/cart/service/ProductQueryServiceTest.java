package cart.service;

import cart.service.dto.ProductSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/schema.sql")
@Sql("/data.sql")
class ProductQueryServiceTest {

    @Autowired
    private ProductQueryService productQueryService;

    @Test
    @DisplayName("searchAllProducts() : 저장된 물품을 모두 조회할 수 있다.")
    void test_searchAllProducts() throws Exception {
        //given
        final int resultSize = 2;
        final String resultName = "피자";

        //when
        final List<ProductSearchResponse> productSearchResponses = productQueryService.searchAllProducts();

        //then
        assertAll(
                () -> assertThat(productSearchResponses).hasSize(resultSize),
                () -> assertEquals(resultName, productSearchResponses.get(0).getName())
        );
    }
}
