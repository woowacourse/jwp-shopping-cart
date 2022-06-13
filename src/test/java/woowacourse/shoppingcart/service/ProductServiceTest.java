package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static woowacourse.shoppingcart.ProductFixture.WATER;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/init.sql")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @ParameterizedTest
    @CsvSource(value = {"2,2,2", "3,2,1", "4,2,0"})
    void 상품_목록_조회_총_상품_갯수는_4개_이다(int size, int page, int productCount) {
        var products = productService.findProducts(size, page);

        assertThat(products.size()).isEqualTo(productCount);
    }

    @Test
    void 상품_ID로_조회() {
        var product = productService.findProductById(1L);

        assertThat(product).isEqualTo(WATER);
    }
}
