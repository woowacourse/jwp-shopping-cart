package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.dto.ProductResponse;

@SpringBootTest
@Sql(scripts = "classpath:truncate.sql")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductDao productDao;

    @DisplayName("상품을 추가하는 기능의 정상 동작 확인")
    @Test
    void addProduct() {
        //given
        final String name = "맥북";
        final Integer price = 250;
        final Long stockQuantity = 10L;
        final String imageUrl = "test.com";
        final String imageAlt = "이미지입니다.";
        //when
        final ProductResponse productResponse = productService
                .addProduct(name, price, stockQuantity, imageUrl, imageAlt);

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

        productService.addProduct(name1, price, stockQuantity, imageUrl, imageAlt);
        productService.addProduct(name2, price, stockQuantity, imageUrl, imageAlt);

        //when
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

        final ProductResponse productResponse = productService
                .addProduct(name, price, stockQuantity, imageUrl, imageAlt);
        //when
        final ProductResponse productResponseById = productService.findProductById(productResponse.getId());
        //then
        assertThat(productResponseById.getName()).isEqualTo(name);
    }
}
