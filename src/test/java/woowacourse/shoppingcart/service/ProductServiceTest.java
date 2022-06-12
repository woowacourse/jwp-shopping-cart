package woowacourse.shoppingcart.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.ProductResponse;
import woowacourse.shoppingcart.dto.ProductsResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql(scripts = {"classpath:testSchema.sql", "classpath:testData.sql"})
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    void 특정_상품을_가져오는_경우() {
        ProductResponse productResponse = productService.findProductById(7L);

        assertAll(() -> assertThat(productResponse.getId()).isEqualTo(7L),
                () -> assertThat(productResponse.getName()).isEqualTo("맛있는 초밥"),
                () -> assertThat(productResponse.getPrice()).isEqualTo(700),
                () -> assertThat(productResponse.getImageUrl()).isEqualTo("https://www.naver.com"));
    }

    @Test
    void 존재하지_않는_상품을_가져오는_경우() {
        assertThatThrownBy(() -> productService.findProductById(300L))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("[ERROR] 없는 상품입니다.");
    }

    @Test
    void 전체_상품을_12개로_끊어서_첫_페이지만_가져오는_경우() {
        ProductsResponse productsResponse = productService.findProducts(1, 12);

        assertThat(productsResponse.getProducts().size()).isEqualTo(12);
    }

    @Test
    void 전체_상품을_12개로_끊어서_마지막_페이지만_가져오는_경우() {
        ProductsResponse productsResponse = productService.findProducts(2, 12);

        assertThat(productsResponse.getProducts().size()).isEqualTo(1);
    }

    @Test 
    void 페이지와_페이지당_사이즈_모두_없이_가져오는_경우() {
        ProductsResponse productsResponse = productService.findProducts(1, 1000);

        assertThat(productsResponse.getProducts().size()).isEqualTo(13);
    }

    @Test
    void 전체_상품의_페이지보다_더_많은_페이지를_요구하는_경우() {
        ProductsResponse productsResponse = productService.findProducts(3, 12);

        assertThat(productsResponse.getProducts().size()).isEqualTo(0);
    }

    @Test
    void 자연수가_아닌_페이지를_요구하는_경우() {
        assertThatThrownBy(() -> productService.findProducts(0, 12))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("[ERROR] 페이지는 자연수여야 합니다.");
    }

    @Test
    void 페이지당_갯수가_자연수가_아닌_경우() {
        assertThatThrownBy(() -> productService.findProducts(1, 0))
                .isInstanceOf(InvalidProductException.class)
                .hasMessage("[ERROR] 페이지당 갯수는 자연수여야 합니다.");
    }

    @Test
    void 전체_상품을_가져오는_경우() {
        ProductsResponse productsResponse = productService.findProducts();

        assertThat(productsResponse.getProducts().size()).isEqualTo(13);
    }
}
