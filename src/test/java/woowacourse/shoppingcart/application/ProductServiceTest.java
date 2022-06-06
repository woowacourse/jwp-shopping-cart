package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
    @Sql("/setProducts.sql")
    @Test
    void findById() {
        // given
        Long productId = 1L;

        // when
        ProductResponse productResponse = productService.findById(productId);

        // then
        assertAll(
                () -> assertThat(productResponse.getId()).isEqualTo(1L),
                () -> assertThat(productResponse.getName()).isEqualTo("apple"),
                () -> assertThat(productResponse.getPrice()).isEqualTo(1000),
                () -> assertThat(productResponse.getImageUrl()).isEqualTo("http://mart/apple")
        );
    }
}
