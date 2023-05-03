package cart.business;

import cart.presentation.dto.ProductRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductServiceTest {

    @Autowired
    private CartService productService;

    @Test
    @DisplayName("동일한 상품을 repository에 insert할시 예외를 던진다")
    void test_perform_exception() {
        //given
        ProductRequest request = new ProductRequest("teo", "https://", 10);

        //when
        productService.createProduct(request);

        //then
        Assertions.assertThatThrownBy(() -> productService.createProduct(request)).isInstanceOf(IllegalArgumentException.class);
    }
}
