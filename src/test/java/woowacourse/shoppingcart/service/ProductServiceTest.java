package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.application.ProductService;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @ParameterizedTest
    @CsvSource(value = {"2,2,2", "3,2,1", "4,2,0"})
    void 상품_목록_조회_총_상품_갯수는_4개_이다(int size, int page, int productCount) {
        var products = productService.findProducts(size, page);

        assertThat(products.size()).isEqualTo(productCount);
    }
}
