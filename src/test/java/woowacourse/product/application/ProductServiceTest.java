package woowacourse.product.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.product.dto.ProductRequest;

@Transactional
@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    private final String name = "짱구";
    private final int price = 100_000_000;
    private final int stock = 1;
    private final String imageURL = "http://example.com/jjanggu.jpg";

    @DisplayName("상품을 추가한다.")
    @Test
    void addProduct() {
        final ProductRequest productRequest = new ProductRequest(name, price, stock, imageURL);

        final Long id = productService.addProduct(productRequest);

        assertThat(id).isNotNull();
    }
}
