package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.PageRequest;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class ProductServiceTest {

    private final ProductService productService;

    @Autowired
    public ProductServiceTest(ProductService productService) {
        this.productService = productService;
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void products() {
        final PageRequest pageRequest = new PageRequest(3, 2);

        assertThat(productService.findProducts(pageRequest))
                .hasSize(2)
                .extracting(Product::getId)
                .contains(5L, 6L);
    }
}
