package woowacourse.shoppingcart.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ProductsTest {

    // given
    private final Products products = new Products(List.of(
            new Product(1L, "치킨", 20_000, "http://example.com/chicken.jpg"),
            new Product(2L, "피자", 10_000, "http://example.com/pizza.jpg"),
            new Product(3L, "햄버거", 5_000, "http://example.com/hamburger.jpg"),
            new Product(4L, "족발", 20_000, "http://example.com/jokbal.jpg"),
            new Product(5L, "보쌈", 22_000, "http://example.com/bossam.jpg"),
            new Product(6L, "떡볶이", 15_000, "http://example.com/ddukbokki.jpg"),
            new Product(7L, "김치", 10_000, "http://example.com/kimchi.jpg")
    ));

    @Test
    @DisplayName("페이지에 띄워줄 상품을 반환한다.")
    void getProductsOfPage() {
        // when
        Products productsOfPage = products.getProductsOfPage(3, 2);
        List<Long> productIds = productsOfPage.getValue().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        // then
        assertThat(productIds).containsExactly(4L, 5L, 6L);
    }

    @Test
    @DisplayName("마지막 페이지에서의 상품을 반환한다.")
    void getProductsOfLastPage() {
        // when
        Products productsOfPage = products.getProductsOfPage(3, 3);
        List<Long> productIds = productsOfPage.getValue().stream()
                .map(Product::getId)
                .collect(Collectors.toList());

        // then
        assertThat(productIds).containsExactly(7L);
    }

    @Test
    @DisplayName("페이지가 반환 가능한 페이지보다 클 시 빈 상품을 반환한다.")
    void getProductsOfEmptyProducts() {
        // when
        Products productsOfPage = products.getProductsOfPage(1000, 3);

        // then
        assertThat(productsOfPage.getValue()).isEmpty();
    }
}
