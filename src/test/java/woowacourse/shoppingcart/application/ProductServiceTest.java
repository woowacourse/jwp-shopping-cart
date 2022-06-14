package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.request.ProductRequest;
import woowacourse.shoppingcart.application.dto.response.ProductResponse;
import woowacourse.shoppingcart.exception.datanotfound.ProductDataNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql"})
@DisplayName("Product 서비스 테스트")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("상품의 아이디를 이용하여 상품을 조회한다.")
    @Test
    void findById() {
        // given
        Long productId = productService.addProduct(new ProductRequest("초콜렛", 1_000, "www.test.com"));

        // when
        ProductResponse productResponse = productService.findById(productId);

        // then
        assertThat(productResponse).usingRecursiveComparison().isEqualTo(
                new ProductResponse(productId, "초콜렛", 1_000, "www.test.com")
        );
    }

    @DisplayName("존재하지 않는 상품을 조회하면 예외가 발생한다.")
    @Test
    void findByIdException() {
        // when & then
        assertThatThrownBy(() -> productService.findById(1L))
                .isInstanceOf(ProductDataNotFoundException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }

    @DisplayName("현재 페이지에 해당하는 상품 목록을 조회한다.")
    @Test
    void findProductsInPage() {
        // given
        productService.addProduct(new ProductRequest("초콜렛", 1_000, "www.test.com"));
        productService.addProduct(new ProductRequest("초콜렛2", 1_000, "www.test2.com"));
        productService.addProduct(new ProductRequest("초콜렛3", 1_000, "www.test3.com"));
        productService.addProduct(new ProductRequest("초콜렛4", 1_000, "www.test4.com"));

        // when
        List<ProductResponse> productResponses = productService.findProductsInPage(1L, 5L);
        List<String> productNames = productResponses.stream()
                .map(ProductResponse::getName)
                .collect(Collectors.toList());

        // then
        assertAll(
                () -> assertThat(productResponses).size().isEqualTo(4),
                () -> assertThat(productNames).contains("초콜렛", "초콜렛2", "초콜렛3", "초콜렛4")
        );
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    @DisplayName("상품 목록을 조회할 때 페이지가 0 이하이면 예외가 발생한다.")
    void findProductsInPageInvalidPageException(Long pageNum) {
        // when & then
        assertThatThrownBy(() -> productService.findProductsInPage(pageNum, 5L))
                .isInstanceOf(ProductDataNotFoundException.class)
                .hasMessage("페이지는 1 이상이어야 합니다.");
    }

    @ParameterizedTest
    @ValueSource(longs = {-1L, 0L})
    @DisplayName("상품 목록을 조회할 때 상품을 조회할 개수가 0 이하이면 예외가 발생한다.")
    void findProductsInPageInvalidLimitException(Long limitCount) {
        // when & then
        assertThatThrownBy(() -> productService.findProductsInPage(1L, limitCount))
                .isInstanceOf(ProductDataNotFoundException.class)
                .hasMessage("상품을 조회할 개수는 1 이상이어야 합니다.");
    }
}
