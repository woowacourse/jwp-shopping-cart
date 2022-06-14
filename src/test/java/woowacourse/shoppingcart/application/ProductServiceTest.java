package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.ProductResponse;

@SpringBootTest
@Sql("/init.sql")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("조회된 상품의 정보를 반환한다.")
    @Test
    void findById() {
        // given
        Long productId = 1L;

        // when
        ProductResponse productResponse = productService.findById(productId);

        // then
        assertAll(
                () -> assertThat(productResponse.getId()).isEqualTo(1L),
                () -> assertThat(productResponse.getName()).isEqualTo("SPC삼립 뉴욕샌드위치식빵 (990g×4ea) BOX"),
                () -> assertThat(productResponse.getPrice()).isEqualTo(12090),
                () -> assertThat(productResponse.getImageUrl()).isEqualTo(
                        "https://cdn-mart.baemin.com/sellergoods/main/678bd8ec-e5fa-4ae2-be55-2cd290b3f10f.jpg"
                )
        );
    }

    @DisplayName("찾은 상품 범위 만큼의 상품 정보들을 반환하는 기능")
    @Test
    void findProductsOfPage() {
        // given
        int page = 2;
        int limit = 5;

        // when
        List<ProductResponse> productsOfPage = productService.findProductsOfPage(page, limit);

        // then
        assertAll(
                () -> assertThat(productsOfPage.size()).isEqualTo(5),
                () -> assertThat(productsOfPage.stream().map(ProductResponse::getId).collect(Collectors.toList()))
                        .containsExactly(6L, 7L, 8L, 9L, 10L)
        );
    }
}
