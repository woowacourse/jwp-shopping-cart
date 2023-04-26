package cart.service;

import cart.dto.ProductRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class CartServiceTest {

    private final CartService cartService;

    @Autowired
    public CartServiceTest(CartService cartService) {
        this.cartService = cartService;
    }

    @DisplayName("상품이 있을 때 update 할 수 있다.")
    @Test
    void updateProduct() {
        //given
        cartService.addProduct(new ProductRequestDto("오션", "이미지", 10000));

        //then
        assertThatNoException().isThrownBy(() -> cartService.updateProduct(new ProductRequestDto(1L, "연어", "이미지", 100)));
    }

    @DisplayName("상품이 없을 때 update 시 예외가 발생한다.")
    @Test
    void updateProduct_Exception() {
        assertThatThrownBy(() -> cartService.updateProduct(new ProductRequestDto()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품이 존재하지 않습니다.");
    }

    @DisplayName("상품이 있을 때 삭제할 수 있다.")
    @Test
    void deleteProduct() {
        //given
        cartService.addProduct(new ProductRequestDto("오션", "이미지", 10000));

        //then
        assertThatNoException().isThrownBy(() -> cartService.deleteProduct(1L));
    }

    @DisplayName("상품이 없을 때 삭제 시 예외가 발생한다.")
    @Test
    void deleteProduct_Exception() {
        assertThatThrownBy(() -> cartService.deleteProduct(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품이 존재하지 않습니다.");
    }
}
