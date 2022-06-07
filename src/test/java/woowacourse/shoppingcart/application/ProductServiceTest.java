package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.shoppingcart.application.dto.ProductResponse;
import woowacourse.shoppingcart.application.dto.ProductSaveRequest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("id 값으로 상품을 조회한다.")
    void findById() {
        // given
        Long saveId = productService.save(new ProductSaveRequest("상품1", 1000, "https://www.test.com"));

        // when
        ProductResponse productResponse = productService.findById(saveId);

        // then
        assertThat(productResponse).usingRecursiveComparison()
                .isEqualTo(new ProductResponse(1L, "상품1", 1000, "https://www.test.com"));
    }
}
