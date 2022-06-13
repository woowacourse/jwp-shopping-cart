package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_REQUEST_1;
import static woowacourse.shoppingcart.fixture.ProductFixtures.PRODUCT_REQUEST_2;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import woowacourse.shoppingcart.dao.JdbcProductDao;
import woowacourse.shoppingcart.dto.ProductResponse;

@JdbcTest
class ProductServiceTest {

    private final ProductService productService;

    @Autowired
    ProductServiceTest(JdbcTemplate jdbcTemplate) {
        final JdbcProductDao productDao = new JdbcProductDao(jdbcTemplate);
        this.productService = new ProductService(productDao);
    }

    @Test
    void addProduct() {
        //given
        final Long id = productService.addProduct(PRODUCT_REQUEST_1);

        //when & then
        assertThat(id).isPositive();
    }

    @Test
    void findProducts() {
        //given
        final Long productId1 = productService.addProduct(PRODUCT_REQUEST_1);
        final Long productId2 = productService.addProduct(PRODUCT_REQUEST_2);

        //when
        final List<ProductResponse> productResponses = productService.findProducts();

        //then
        assertThat(productResponses)
                .extracting("id", "name", "price", "imageUrl", "description", "stock")
                .containsExactly(
                        tuple(productId1, PRODUCT_REQUEST_1.getName(), PRODUCT_REQUEST_1.getPrice(),
                                PRODUCT_REQUEST_1.getImageUrl(), PRODUCT_REQUEST_1.getDescription(),
                                PRODUCT_REQUEST_1.getStock()),
                        tuple(productId2, PRODUCT_REQUEST_2.getName(), PRODUCT_REQUEST_2.getPrice(),
                                PRODUCT_REQUEST_2.getImageUrl(), PRODUCT_REQUEST_2.getDescription(),
                                PRODUCT_REQUEST_2.getStock())
                );
    }

    @Test
    void findProductById() {
        //given
        final Long id = productService.addProduct(PRODUCT_REQUEST_1);

        //when
        final ProductResponse productResponse = productService.findProductById(id);

        //then
        assertThat(productResponse).extracting("id", "name", "price", "imageUrl", "description", "stock")
                .containsExactly(id, PRODUCT_REQUEST_1.getName(), PRODUCT_REQUEST_1.getPrice(),
                        PRODUCT_REQUEST_1.getImageUrl(), PRODUCT_REQUEST_1.getDescription(),
                        PRODUCT_REQUEST_1.getStock());
    }

    @Test
    void deleteProductById() {
        //given
        final Long id = productService.addProduct(PRODUCT_REQUEST_1);
        final int beforeSize = productService.findProducts().size();

        //when
        productService.deleteProductById(id);
        final int afterSize = productService.findProducts().size();

        //then
        assertThat(beforeSize - 1).isEqualTo(afterSize);
    }
}