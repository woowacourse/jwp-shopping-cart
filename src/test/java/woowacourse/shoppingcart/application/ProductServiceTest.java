package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import woowacourse.shoppingcart.application.dto.ProductResponse;
import woowacourse.shoppingcart.exception.domain.ProductNotFoundException;
import woowacourse.shoppingcart.ui.dto.ProductRequest;
import woowacourse.support.test.ExtendedApplicationTest;

@ExtendedApplicationTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("상품을 추가한다.")
    @Test
    void addProduct() {
        // given
        ProductRequest request = new ProductRequest("productName", 1000, "http://example.com", "desc");
        final Long productId = productService.addProduct(request);
        // when
        final ProductResponse saved = productService.findProductById(productId);
        // then
        assertThat(saved.getId()).isNotNull();
    }

    @DisplayName("상품을 조회한다.")
    @Test
    void findProductById() {
        // given
        ProductRequest request = new ProductRequest("productName", 1000, "http://example.com", "desc");
        final Long productId = productService.addProduct(request);
        // when
        final ProductResponse saved = productService.findProductById(productId);
        // then
        assertThat(saved.getName()).isEqualTo("productName");
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void findProducts() {
        // given
        productService.addProduct(
            new ProductRequest("productName1", 1000, "http://example.com", "desc")
        );
        productService.addProduct(
            new ProductRequest("productName2", 1000, "http://example.com", "desc")
        );

        // when
        final List<ProductResponse> products = productService.findProducts();
        // then
        assertThat(products).hasSize(2);
    }

    @DisplayName("ID로 상품을 삭제한다.")
    @Test
    void deleteById() {
        // given
        final Long productId = productService.addProduct(
            new ProductRequest("productName1", 1000, "http://example.com", "desc")
        );
        // when
        productService.deleteProductById(productId);
        // then
        assertThatExceptionOfType(ProductNotFoundException.class)
            .isThrownBy(() -> productService.findProductById(productId));
    }
}