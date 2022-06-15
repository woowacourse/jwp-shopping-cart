package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.product.ProductRequest;
import woowacourse.shoppingcart.dto.product.ProductsRequest;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:products.sql"})
public class ProductServiceTest {

    private final ProductService productService;

    @Autowired
    public ProductServiceTest(ProductService productService) {
        this.productService = productService;
    }

    @DisplayName("첫 5가지 상품 목록을 반환한다.")
    @Test
    void findProducts() {
        ProductsRequest productsRequest = new ProductsRequest(1, 5);

        List<Product> products = productService.findProducts(productsRequest);

        assertThat(products.size()).isEqualTo(5);
    }

    @DisplayName("마지막 5개 제한 상품 목록을 반환할 때 4개의 상품 목록을 반환한다.")
    @Test
    void findLastProducts() {
        ProductsRequest productsRequest = new ProductsRequest(4, 5);

        List<Product> products = productService.findProducts(productsRequest);

        assertThat(products.size()).isEqualTo(4);
    }

    @DisplayName("상품을 추가하고 상품의 id 를 반환한다.")
    @Test
    void addProduct() {
        ProductRequest productRequest = new ProductRequest(null, "test", 1000, "test", 100);

        Long productId = productService.addProduct(productRequest);

        assertThat(productId).isEqualTo(20L);
    }

    @DisplayName("productId로 상품을 찾는다.")
    @Test
    void findProductById() {
        Product product = productService.findProductById(1L);

        assertAll(
                () -> assertThat(product.getName()).isEqualTo("캠핑 의자"),
                () -> assertThat(product.getPrice()).isEqualTo(35000),
                () -> assertThat(product.getStock()).isEqualTo(100)
        );
    }

    @DisplayName("productId로 상품을 삭제한다.")
    @Test
    void deleteProductById() {
        productService.deleteProductById(19L);

        ProductsRequest productsRequest = new ProductsRequest(1, 20);

        assertThat(productService.findProducts(productsRequest).size()).isEqualTo(18);
    }
}
