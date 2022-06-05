package woowacourse.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.product.dto.ProductRequest;
import woowacourse.product.dto.ProductResponse;

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

    @DisplayName("입력한 id에 맞는 단일 상품을 조회한다.")
    @Test
    void findProduct() {
        final ProductRequest productRequest = new ProductRequest(name, price, stock, imageURL);
        final Long id = productService.addProduct(productRequest);

        final ProductResponse findProduct = productService.findProductById(id);

        assertAll(
            () -> assertThat(findProduct.getId()).isEqualTo(id),
            () -> assertThat(findProduct.getName()).isEqualTo(name),
            () -> assertThat(findProduct.getPrice()).isEqualTo(price),
            () -> assertThat(findProduct.getStock()).isEqualTo(stock),
            () -> assertThat(findProduct.getImageURL()).isEqualTo(imageURL)
        );
    }

    @DisplayName("입력한 id에 맞는 상품을 삭제한다.")
    @Test
    void deleteProduct() {
        final ProductRequest productRequest = new ProductRequest(name, price, stock, imageURL);
        final Long id = productService.addProduct(productRequest);

        assertDoesNotThrow(() -> productService.deleteProductById(id));
    }
}
