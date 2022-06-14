package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.common.exception.NotFoundException;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.ThumbnailImage;
import woowacourse.shoppingcart.dto.ProductRequest;
import woowacourse.shoppingcart.dto.ProductResponse;

@SpringBootTest
@Sql("classpath:truncate.sql")
class ProductServiceTest {
    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("상품을 찾을 때 해당 값이 없을 경우 에러를 발생시킨다.")
    void findProductFailByNotFound() {
        //given
        ThumbnailImage image = new ThumbnailImage("url", "alt");
        ProductRequest productRequest = new ProductRequest("name", 1000, 10, image);
        productService.addProduct(productRequest);

        //then
        assertThatThrownBy(() -> productService.findProductById(2L))
                .isInstanceOf(NotFoundException.class);
    }

    @Test
    @DisplayName("상품을 찾을 때 해당 값이 존재하는 경우 해당 값을 리턴한다.")
    void findProduct() {
        //given
        ThumbnailImage image = new ThumbnailImage("url", "alt");
        ProductRequest productRequest = new ProductRequest("name", 1000, 10, image);
        Product product = new Product(1L, "name", 1000, 10, image);

        productService.addProduct(productRequest);

        //when
        ProductResponse actual = productService.findProductById(1L);
        ProductResponse expected = new ProductResponse(product);

        //then
        assertThat(product).usingRecursiveComparison().isEqualTo(expected);
    }
}