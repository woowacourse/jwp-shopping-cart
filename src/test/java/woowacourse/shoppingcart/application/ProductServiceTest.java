package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import javax.sql.DataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.exception.InvalidProductException;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.ProductDao;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:init.sql")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class ProductServiceTest {

    private final ProductService productService;

    ProductServiceTest(final DataSource dataSource) {
        final ProductDao productDao = new ProductDao(dataSource);
        this.productService = new ProductService(productDao);
    }

    @DisplayName("상품 목록을 조회한다.")
    @Test
    void findProducts() {
        List<ProductResponse> products = productService.findProducts();
        assertThat(products.size()).isEqualTo(2);
    }

    @DisplayName("상품을 단건 조회한다.")
    @Test
    void findProduct() {
        ProductResponse product = productService.findProductById(1L);
        assertThat(product)
                .extracting("name", "price", "image")
                .containsExactly("우유", 3000, "http://example1.com");
    }

    @DisplayName("존재하지 않는 아이디로 조회하면 에러가 발생한다.")
    @Test
    void findProductByInvalidId() {
        assertThatThrownBy(() -> productService.findProductById(3L))
                .isInstanceOf(InvalidProductException.class);
    }
}
