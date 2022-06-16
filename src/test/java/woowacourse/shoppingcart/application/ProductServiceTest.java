package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

@SuppressWarnings("NonAsciiChracters")
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void findProducts메서드는_상품_목록들을_가져온다() {
        List<ProductResponse> products = productService.findProducts();

        assertThat(products).hasSize(30);
    }

    @Test
    void addProduct메서드는_상품을_등록한다() {
        final ProductRequest request = new ProductRequest("테스트 상품", 1_000, "test_url");

        Long productId = productService.addProduct(request);

        assertThat(productId).isEqualTo(31L);
    }

    @Test
    void findProductById메서드는_상품id로_상품을_찾는다() {
        final Long productId = 1L;

        assertThat(productService.findProductById(productId))
            .extracting("id", "name", "price", "imageUrl")
            .containsExactly(1L, "[승팡] 칠레산 코호 냉동 연어필렛 trim D(껍질있음) 1.1~1.3kg",
                24_500, "https://cdn-mart.baemin.com/sellergoods/main/92438f0e-0c4b-425e-b03b-999cee7cdca2.jpg");
    }

    @Test
    void deleteProductById메서드는_상품id로_상품을_삭제한다() {
        final Long productId = 1L;

        productService.deleteProductById(productId);

        assertThatThrownBy(() -> productService.findProductById(1L))
            .isInstanceOf(InvalidProductException.class);
    }
}
