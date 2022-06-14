package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.product.ProductCreateRequest;
import woowacourse.shoppingcart.dto.product.ProductResponse;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductServiceTest {

    private final ProductService productService;
    private final ProductDao productDao;

    public ProductServiceTest(JdbcTemplate jdbcTemplate) {
        this.productDao = new ProductDao(jdbcTemplate);
        this.productService = new ProductService(productDao);
    }

    @DisplayName("상품을 저장한다")
    @Test
    void addProduct() {
        // given
        ProductCreateRequest product = new ProductCreateRequest("testName", 10000, "imageUrl", 10);

        // when
        Long newProductId = productService.addProduct(product);

        // then
        assertThat(newProductId).isEqualTo(1L);
    }

    @DisplayName("상품을 ID로 조회한다")
    @Test
    void findProductById() {
        // given
        ProductCreateRequest product = new ProductCreateRequest("testName", 10000, "imageUrl", 10);
        Long newProductId = productService.addProduct(product);

        // when
        ProductResponse findProduct = productService.findProductById(newProductId);
        ProductResponse expected = new ProductResponse(newProductId, "testName", 10000, "imageUrl", 10);

        // then
        assertThat(findProduct).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @DisplayName("전체 상품을 조회한다")
    @Test
    void findProducts() {
        // given
        ProductCreateRequest product = new ProductCreateRequest("testName", 10000, "imageUrl", 10);
        ProductCreateRequest product2 = new ProductCreateRequest("testName2", 20000, "imageUrl2", 20);
        ProductCreateRequest product3 = new ProductCreateRequest("testName3", 30000, "imageUrl3", 30);

        productService.addProduct(product);
        productService.addProduct(product2);
        productService.addProduct(product3);

        // when
        List<ProductResponse> products = productService.findProducts();

        // then
        assertThat(products).hasSize(3);
    }

    @DisplayName("상품을 삭제한다")
    @Test
    void deleteProductById() {
        // given
        ProductCreateRequest product = new ProductCreateRequest("testName", 10000, "imageUrl", 10);
        Long savedId = productService.addProduct(product);

        // when
        productService.deleteProductById(savedId);
        List<ProductResponse> products = productService.findProducts();

        // then
        assertThat(products).isEmpty();
    }
}
