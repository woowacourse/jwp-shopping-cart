package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.application.dto.ProductServiceRequest;
import woowacourse.shoppingcart.exception.ProductNotFoundException;
import woowacourse.shoppingcart.ui.dto.ProductResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
class ProductServiceTest {

    private final ProductService productService;
    private final String name = "초콜렛";
    private final int price = 1_000;
    private final String imageUrl = "www.test.com";

    public ProductServiceTest(ProductService productService) {
        this.productService = productService;
    }

    @DisplayName("올바른 데이터로 상품을 저장하면 ID를 반환한다.")
    @Test
    void add() {
        ProductServiceRequest request = new ProductServiceRequest(name, price, imageUrl);
        long productId = productService.add(request);

        assertThat(productId).isEqualTo(18L);
    }

    @DisplayName("올바른 상품 ID로 상품을 조회한다.")
    @Test
    void findProduct() {
        ProductServiceRequest request = new ProductServiceRequest(name, price, imageUrl);
        long productId = productService.add(request);

        ProductResponse response = productService.findProduct(productId);

        assertAll(
                () -> assertThat(response.getId()).isEqualTo(productId),
                () -> assertThat(response.getName()).isEqualTo(name),
                () -> assertThat(response.getPrice()).isEqualTo(price),
                () -> assertThat(response.getImageUrl()).isEqualTo(imageUrl)
        );
    }

    @DisplayName("잘못된 상품 ID로 상품 조회시 예외가 발생한다.")
    @Test
    void findProductByNotExistId() {
        assertThatThrownBy(() -> productService.findProduct(100L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("존재하지 않는 상품입니다.");
    }

    @DisplayName("등록된 상품들을 조회한다.")
    @Test
    void findProducts() {
        List<ProductResponse> products = productService.findProducts();

        assertThat(products.size()).isEqualTo(17);
    }

    @DisplayName("올바른 상품 ID로 상품을 삭제한다.")
    @Test
    void deleteProductById() {
        ProductServiceRequest request = new ProductServiceRequest(name, price, imageUrl);
        long productId = productService.add(request);

        productService.deleteProduct(productId);

        assertThatThrownBy(() -> productService.findProduct(100L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("존재하지 않는 상품입니다.");
    }
}
