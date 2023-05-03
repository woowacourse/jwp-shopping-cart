package cart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import cart.service.dto.ProductModifyRequest;
import cart.service.dto.ProductRegisterRequest;
import cart.service.dto.ProductSearchResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql("/schema.sql")
@Sql("/data.sql")
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Test
    @DisplayName("registerProduct() : 물품을 등록할 수 있다.")
    void test_registerProduct() throws Exception {
        //given
        final String name = "피자";
        final int price = 10000;
        final String imageUrl = "imageUrl";

        final ProductRegisterRequest productRegisterRequest = new ProductRegisterRequest(name, price, imageUrl);

        //when
        final int beforeSize = productService.searchAllProducts().size();

        productService.registerProduct(productRegisterRequest);

        final int afterSize = productService.searchAllProducts().size();

        //then
        assertEquals(afterSize, beforeSize + 1);
    }

    @Test
    @DisplayName("searchAllProducts() : 저장된 물품을 모두 조회할 수 있다.")
    void test_searchAllProducts() throws Exception {
        //given
        final int resultSize = 2;
        final String resultName = "피자";

        //when
        final List<ProductSearchResponse> productSearchResponses = productService.searchAllProducts();

        //then
        assertAll(
                () -> assertThat(productSearchResponses).hasSize(resultSize),
                () -> assertEquals(resultName, productSearchResponses.get(0).getName())
        );
    }

    @Test
    @DisplayName("deleteProduct() : 물품을 삭제할 수 있다.")
    void test_deleteProduct() throws Exception {
        //given
        final Long id = 3L;

        //when
        final int beforeSize = productService.searchAllProducts().size();

        productService.deleteProduct(id);

        final int afterSize = productService.searchAllProducts().size();

        //then
        assertEquals(afterSize, beforeSize - 1);
    }

    @Test
    @DisplayName("modifyProduct() : 물품을 수정할 수 있다.")
    void test_modifyProduct() throws Exception {
        //given
        final Long id = 3L;
        final String name = "수정된 피자";
        final int price = 20000;
        final String imageUrl = "수정된 imageUrl";

        final ProductModifyRequest productModifyRequest = new ProductModifyRequest(name, price, imageUrl);

        //when
        productService.modifyProduct(id, productModifyRequest);

        final ProductSearchResponse productSearchResponse = productService.searchAllProducts()
                .stream()
                .filter(it -> it.getId().equals(id))
                .findFirst()
                .orElseThrow();

        //then
        assertAll(
                () -> assertEquals(name, productSearchResponse.getName()),
                () -> assertEquals(price, productSearchResponse.getPrice()),
                () -> assertEquals(imageUrl, productSearchResponse.getImageUrl())
        );
    }
}
