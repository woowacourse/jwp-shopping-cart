package cart.service;

import cart.service.dto.ProductModifyRequest;
import cart.service.dto.ProductRegisterRequest;
import cart.service.dto.ProductSearchResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/schema.sql")
@Sql("/data.sql")
class ProductCommandServiceTest {

    @Autowired
    private ProductCommandService productCommandService;

    @Autowired
    private ProductQueryService productQueryService;

    @Test
    @DisplayName("registerProduct() : 물품을 등록할 수 있다.")
    void test_registerProduct() throws Exception {
        //given
        final String name = "피자";
        final int price = 10000;
        final String imageUrl = "imageUrl";

        final ProductRegisterRequest productRegisterRequest = new ProductRegisterRequest(name, price, imageUrl);

        //when
        final int beforeSize = productQueryService.searchAllProducts().size();

        productCommandService.registerProduct(productRegisterRequest);

        final int afterSize = productQueryService.searchAllProducts().size();

        //then
        assertEquals(afterSize, beforeSize + 1);
    }

    @Test
    @DisplayName("deleteProduct() : 물품을 삭제할 수 있다.")
    void test_deleteProduct() throws Exception {
        //given
        final Long id = 3L;

        //when
        final int beforeSize = productQueryService.searchAllProducts().size();

        productCommandService.deleteProduct(id);

        final int afterSize = productQueryService.searchAllProducts().size();

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
        productCommandService.modifyProduct(id, productModifyRequest);

        final ProductSearchResponse productSearchResponse = productQueryService.searchAllProducts()
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
