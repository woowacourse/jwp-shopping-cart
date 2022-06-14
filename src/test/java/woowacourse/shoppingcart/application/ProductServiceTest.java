package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
@Transactional
@Sql("/test_schema.sql")
class ProductServiceTest {
    private final ProductRequest productRequest1 =
            new ProductRequest("감자", 200, "https://example.com/potato.jpg");
    private final ProductRequest productRequest2 =
            new ProductRequest("햇반", 400, "https://example.com/rice.jpg");

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    @DisplayName("상품을 성공적으로 등록한다.")
    @Test
    void addProduct() {
        Long productId = productService.addProduct(productRequest1);

        Product actual = productDao.findProductById(productId)
                .orElseThrow(() -> new InvalidProductException("존재하지 않는 상품입니다."));

        assertThat(actual.getName()).isEqualTo(productRequest1.getName());
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void findAllProducts() {
        productService.addProduct(productRequest1);
        productService.addProduct(productRequest2);

        ProductsResponse actual = productService.findProducts();

        assertThat(actual.getProducts().size()).isEqualTo(2);
    }

    @DisplayName("상품 아이디로 단일 상품을 조회한다.")
    @Test
    void findProduct() {
        productService.addProduct(productRequest1);
        Long product2Id = productService.addProduct(productRequest2);

        ProductResponse actual = productService.findProductById(product2Id);

        assertThat(actual.getName()).isEqualTo(productRequest2.getName());
    }

    @DisplayName("올바르지 않은 아이디로 상품을 조회할 경우 예외를 발생시킨다.")
    @Test
    void findProduct_invalidProductId() {
        Long product1Id = productService.addProduct(productRequest1);

        assertThatExceptionOfType(InvalidProductException.class)
                .isThrownBy(() -> productService.findProductById(product1Id + 1))
                .withMessageContaining("존재");
    }

    @DisplayName("상품 아이디로 상품을 삭제한다.")
    @Test
    void deleteProduct() {
        Long product1Id = productService.addProduct(productRequest1);
        productService.deleteProductById(product1Id);

        List<Product> actual = productDao.findProducts();

        assertThat(actual.size()).isEqualTo(0);
    }
}