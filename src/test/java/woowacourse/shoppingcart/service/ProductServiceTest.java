package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ImageDto;
import woowacourse.shoppingcart.dto.ProductRequest;

@SpringBootTest
@Sql(scripts = "classpath:truncate.sql")
public class ProductServiceTest {

    private ProductService productService;

    @Autowired
    public ProductServiceTest(final JdbcTemplate jdbcTemplate) {
        this.productService = new ProductService(new ProductDao(jdbcTemplate));
    }

    @DisplayName("상품을 추가한다.")
    @Test
    void addProduct() {
        // given
        ProductRequest request = new ProductRequest("치킨", 10_000, 20,
                new ImageDto("IMAGE_URL", "IMAGE_ALT"));

        // when
        Long productId = productService.addProduct(request);

        // then
        Product product = productService.findById(productId);

        assertAll(
                () -> assertThat(product.getName()).isEqualTo("치킨"),
                () -> assertThat(product.getPrice()).isEqualTo(10_000),
                () -> assertThat(product.getStockQuantity()).isEqualTo(20)
        );
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void findProducts() {
        // given
        ProductRequest request1 = new ProductRequest("치킨", 10_000, 20,
                new ImageDto("IMAGE_URL", "IMAGE_ALT"));
        ProductRequest request2 = new ProductRequest("피자", 20_000, 10,
                new ImageDto("IMAGE_URL", "IMAGE_ALT"));
        productService.addProduct(request1);
        productService.addProduct(request2);

        // when
        List<Product> products = productService.findProducts();

        // then
        assertAll(
                () -> assertThat(products.get(0).getName()).isEqualTo("치킨"),
                () -> assertThat(products.get(0).getPrice()).isEqualTo(10_000),
                () -> assertThat(products.get(0).getStockQuantity()).isEqualTo(20),
                () -> assertThat(products.get(1).getName()).isEqualTo("피자"),
                () -> assertThat(products.get(1).getPrice()).isEqualTo(20_000),
                () -> assertThat(products.get(1).getStockQuantity()).isEqualTo(10)
        );
    }

    @DisplayName("개별 상품을 조회한다.")
    @Test
    void findProduct() {
        // given
        ProductRequest request = new ProductRequest("치킨", 10_000, 20,
                new ImageDto("IMAGE_URL", "IMAGE_ALT"));
        Long productId = productService.addProduct(request);

        // when
        Product product = productService.findById(productId);

        // then
        assertAll(
                () -> assertThat(product.getName()).isEqualTo("치킨"),
                () -> assertThat(product.getPrice()).isEqualTo(10_000),
                () -> assertThat(product.getStockQuantity()).isEqualTo(20)
        );
    }

    @DisplayName("상품을 삭제한다.")
    @Test
    void deleteProductById() {
        // given
        ProductRequest request = new ProductRequest("치킨", 10_000, 20,
                new ImageDto("IMAGE_URL", "IMAGE_ALT"));
        Long productId = productService.addProduct(request);

        // when
        productService.deleteById(productId);

        // then
        List<Product> products = productService.findProducts();
        assertThat(products).isEmpty();
    }
}
