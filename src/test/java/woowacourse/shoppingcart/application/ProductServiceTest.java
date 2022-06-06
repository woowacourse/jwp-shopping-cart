package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:import.sql"})
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("상품 id와 구매 수량을 받아, 구매할 수 있는지 반환한다.")
    @ParameterizedTest
    @CsvSource({"100, false", "101, true"})
    void isPossibleQuantity(int purchasingQuantity, boolean expected) {
        boolean actual = productService.isImpossibleQuantity(1L, purchasingQuantity);

        assertThat(actual).isEqualTo(expected);
    }
}