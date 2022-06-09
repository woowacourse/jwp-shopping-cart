package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.shoppingcart.ShoppingCartTest;
import woowacourse.shoppingcart.dto.ProductResponse;

@SpringBootTest
class ProductServiceTest extends ShoppingCartTest {

    @Autowired
    private ProductService productService;

    @DisplayName("장바구니에 담으려는 상품의 수량이 재고보다 많다면 예외를 발생시킨다.")
    @Test
    void validateStock() {
        assertThatThrownBy(() -> productService.validateStock(1L, 101))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 재고가 부족합니다.");
    }

    @DisplayName("장바구니에 담으려는 상품의 id가 존재하지 않는다면 예외를 발생시킨다.")
    @Test
    void validateProductId() {
        assertThatThrownBy(() -> productService.validateProductId(20L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }

    @DisplayName("상품 목록을 페이징 조회한다.")
    @Test
    void findProducts() {
        List<ProductResponse> products = productService.findProducts(1, 6);
        List<Long> productIds = products.stream()
                .map(ProductResponse::getId)
                .collect(Collectors.toUnmodifiableList());

        assertThat(productIds).containsExactly(1L, 2L, 3L, 4L, 5L, 6L);
    }

    @DisplayName("상품 목록을 페이징 조회할 때, page의 값 혹은 한 페이지에 보여줄 상품의 개수가 1보다 작다면 예외를 발생시킨다.")
    @ParameterizedTest
    @CsvSource({"1, 0, 한 페이지에 보여줄 상품의 개수는 양수여야합니다.", "0, 1, page 값은 양수여야 합니다."})
    void findProducts_InvalidPaginationValue(int page, int productCountLimit, String expectedExceptionMessage) {
        assertThatThrownBy(() -> productService.findProducts(page, productCountLimit))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(expectedExceptionMessage);
    }
}
