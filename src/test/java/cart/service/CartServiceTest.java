package cart.service;

import cart.dto.ProductResponseDto;
import cart.dto.ProductSaveRequestDto;
import cart.dto.ProductUpdateRequestDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
class CartServiceTest {

    private CartService cartService;

    @Autowired
    public CartServiceTest(CartService cartService) {
        this.cartService = cartService;
    }

    @DisplayName("상품이 있을 때 update 할 수 있다.")
    @Test
    void updateProduct() {
        //given
        cartService.addProduct(new ProductSaveRequestDto("오션", "이미지", 10000));
        ProductResponseDto product = cartService.findProducts().get(0);
        //then
        assertThatNoException().isThrownBy(() -> cartService.updateProduct(new ProductUpdateRequestDto(product.getId(), "연어", "이미지", 100)));
    }

    @DisplayName("상품이 없을 때 update 시 예외가 발생한다.")
    @Test
    void updateProduct_Exception() {
        assertThatThrownBy(() -> cartService.updateProduct(new ProductUpdateRequestDto()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품이 존재하지 않습니다.");
    }

    @DisplayName("상품이 있을 때 삭제할 수 있다.")
    @Test
    void deleteProduct() {
        //given
        cartService.addProduct(new ProductSaveRequestDto("오션", "이미지", 10000));
        ProductResponseDto product = cartService.findProducts().get(0);

        //then
        assertThatNoException().isThrownBy(() -> cartService.deleteProduct(product.getId()));
    }

    @DisplayName("상품이 없을 때 삭제 시 예외가 발생한다.")
    @Test
    void deleteProduct_Exception() {
        assertThatThrownBy(() -> cartService.deleteProduct(1L))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("해당 상품이 존재하지 않습니다.");
    }
}
