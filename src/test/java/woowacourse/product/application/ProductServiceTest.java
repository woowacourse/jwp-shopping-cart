package woowacourse.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static woowacourse.product.dto.ProductResponse.InnerProductResponse;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.product.dto.ProductRequest;
import woowacourse.product.dto.ProductResponse;
import woowacourse.product.dto.ProductResponses;

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

    @DisplayName("상품 전체를 조회한다.")
    @Test
    void findProducts() {
        final ProductRequest productRequest1 = new ProductRequest(name, price, stock, imageURL);
        final Long id1 = productService.addProduct(productRequest1);
        final ProductRequest productRequest2 = new ProductRequest("짱아", price, stock, imageURL);
        final Long id2 = productService.addProduct(productRequest2);

        final ProductResponses findProducts = productService.findProducts();

        final List<Long> findIds = findProducts.getProducts().stream()
            .map(InnerProductResponse::getId)
            .collect(Collectors.toList());

        assertThat(findIds).contains(id1, id2);
    }

    @DisplayName("입력한 id에 맞는 단일 상품을 조회한다.")
    @Test
    void findProduct() {
        final ProductRequest productRequest = new ProductRequest(name, price, stock, imageURL);
        final Long id = productService.addProduct(productRequest);

        final ProductResponse productResponse = productService.findProductById(id);
        final InnerProductResponse findProduct = productResponse.getProduct();

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
