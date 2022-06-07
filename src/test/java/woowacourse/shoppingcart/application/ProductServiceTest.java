package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.shoppingcart.dto.ProductResponse;

@SpringBootTest
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
}
