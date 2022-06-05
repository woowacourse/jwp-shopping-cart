package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@SpringBootTest
@Sql("/init.sql")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("상품 목록 조회")
    @Test
    void findProducts() {
        List<Product> products = productService.findProducts();
        List<String> names = products.stream()
            .map(Product::getName)
            .collect(Collectors.toUnmodifiableList());

        assertAll(
            () -> assertThat(products).hasSize(3),
            () -> assertThat(names).containsExactlyInAnyOrder("칫솔", "치약", "비누")
        );
    }

    @DisplayName("상품 조회")
    @Test
    void findProduct() {
        Product product = productService.findProductById(1L);

        assertThat(product.getName()).isEqualTo("치약");
        assertThat(product.getPrice()).isEqualTo(1600);
    }

    @DisplayName("존재하지 않는 상품 조회 시 예외 발생")
    @Test
    void findNotExistProduct() {
        assertThatThrownBy(() -> productService.findProductById(20L))
            .isInstanceOf(NotFoundProductException.class);
    }
}
