package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.dto.response.ProductResponses;

@SpringBootTest
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("해당하는 페이지의 상품들을 찾을 수 있다.")
    void findProducts() {
        // given
        productService.addProduct(new ProductRequest("치킨", 20_000, "http://example.com/chicken.jpg"));
        productService.addProduct(new ProductRequest("맥주", 20_000, "http://example.com/beer.jpg"));
        productService.addProduct(new ProductRequest("피자", 20_000, "http://example.com/pizza.jpg"));
        productService.addProduct(new ProductRequest("떡볶이", 20_000, "http://example.com/ddukbokki.jpg"));
        productService.addProduct(new ProductRequest("보쌈", 20_000, "http://example.com/bossam.jpg"));
        productService.addProduct(new ProductRequest("족발", 20_000, "http://example.com/jokbal.jpg"));
        productService.addProduct(new ProductRequest("김치", 20_000, "http://example.com/kimchi.jpg"));

        // when
        ProductResponses productResponses = productService.findProducts(3, 2);

        // then
        assertThat(productResponses.getProducts()).size().isEqualTo(3);
    }

    @Test
    @DisplayName("해당하는 페이지가 없으면 빈 상품리스트를 반환한다.")
    void findProductsWithEmpty() {
        // given
        productService.addProduct(new ProductRequest("치킨", 20_000, "http://example.com/chicken.jpg"));
        productService.addProduct(new ProductRequest("맥주", 20_000, "http://example.com/beer.jpg"));
        productService.addProduct(new ProductRequest("피자", 20_000, "http://example.com/pizza.jpg"));
        productService.addProduct(new ProductRequest("떡볶이", 20_000, "http://example.com/ddukbokki.jpg"));
        productService.addProduct(new ProductRequest("보쌈", 20_000, "http://example.com/bossam.jpg"));
        productService.addProduct(new ProductRequest("족발", 20_000, "http://example.com/jokbal.jpg"));
        productService.addProduct(new ProductRequest("김치", 20_000, "http://example.com/kimchi.jpg"));

        // when
        ProductResponses productResponses = productService.findProducts(1000, 5);

        // then
        assertThat(productResponses.getProducts()).size().isEqualTo(0);
    }

    @Test
    @DisplayName("식별자로 상품을 찾을 수 있다.")
    void findProductById() {
        // given
        Long productId = productService.addProduct(new ProductRequest("치킨", 20_000, "http://example.com/chicken.jpg"));

        // when
        ProductResponse productResponse = productService.findProductById(productId);

        // then
        assertThat(productResponse.getName()).isEqualTo("치킨");
    }
}
