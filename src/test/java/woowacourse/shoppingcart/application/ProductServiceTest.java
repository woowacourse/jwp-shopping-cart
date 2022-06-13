package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.product.application.ProductService;
import woowacourse.shoppingcart.product.application.dto.ProductDto;
import woowacourse.shoppingcart.product.ui.dto.ProductRequest;
import woowacourse.shoppingcart.product.ui.dto.ProductResponse;
import woowacourse.shoppingcart.product.ui.dto.ThumbnailImageDto;

@SpringBootTest
@Sql(scripts = "classpath:truncate.sql")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @DisplayName("상품을 추가하는 기능의 정상 동작 확인")
    @Test
    void addProduct() {
        //given
        final String name = "맥북";
        final Integer price = 250;
        final Long stockQuantity = 10L;
        final String imageUrl = "test.com";
        final String imageAlt = "이미지입니다.";
        final ProductRequest productRequest = new ProductRequest(name, price, stockQuantity,
                new ThumbnailImageDto(imageUrl, imageAlt));
        //when
        final ProductResponse productResponse = productService
                .addProduct(ProductDto.from(productRequest));

        //then
        assertThat(productResponse.getName()).isEqualTo(name);
    }

    @DisplayName("여러 상품을 받아오는 기능의 정상 동작 확인")
    @Test
    void findProducts() {
        //given
        final String name1 = "맥북";
        final String name2 = "애플워치";
        final Integer price = 250;
        final Long stockQuantity = 10L;
        final String imageUrl = "test.com";
        final String imageAlt = "이미지입니다.";
        final ProductRequest productRequest1 = new ProductRequest(name1, price, stockQuantity,
                new ThumbnailImageDto(imageUrl, imageAlt));
        final ProductRequest productRequest2 = new ProductRequest(name2, price, stockQuantity,
                new ThumbnailImageDto(imageUrl, imageAlt));

        productService.addProduct(ProductDto.from(productRequest1));
        productService.addProduct(ProductDto.from(productRequest2));
        //whe
        final List<ProductResponse> productResponses = productService.findProducts();

        //then
        final List<String> productNames = productResponses.stream()
                .map(ProductResponse::getName)
                .collect(Collectors.toUnmodifiableList());

        assertThat(productNames).containsExactly(name1, name2);
    }

    @DisplayName("id 값으로 상품을 찾는 기능의 정상 동작 확인")
    @Test
    void findProductById() {
        //given
        final String name = "맥북";
        final Integer price = 250;
        final Long stockQuantity = 10L;
        final String imageUrl = "test.com";
        final String imageAlt = "이미지입니다.";
        final ProductRequest productRequest = new ProductRequest(name, price, stockQuantity,
                new ThumbnailImageDto(imageUrl, imageAlt));

        final ProductResponse productResponse = productService
                .addProduct(ProductDto.from(productRequest));
        //when
        final ProductResponse productResponseById = productService.findProductById(productResponse.getId());
        //then
        assertThat(productResponseById.getName()).isEqualTo(name);
    }
}
