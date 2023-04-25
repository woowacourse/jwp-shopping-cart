package cart.service;

import cart.controller.ProductRegisterRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
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

        //when & then
        assertDoesNotThrow(() -> productService.registerProduct(productRegisterRequest));
    }
}
