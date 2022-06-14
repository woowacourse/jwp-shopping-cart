package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.ProductsRequest;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

@SpringBootTest
@Sql("/initSchema.sql")
@Transactional
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Test
    @DisplayName("이미 존재하는 상품을 장바구니에 추가하려고 하면 예외가 발생한다.")
    void addCartItemException() {
        assertThatThrownBy(() -> cartService.addCartItem(1L, new CartRequest(1L, 10)))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessageContaining("장바구니에 이미 존재하는 상품입니다.");
    }

    @Test
    @DisplayName("장바구니 아이템 목록을 조회한다.")
    void findCartItems() {
        CartItemsResponse cartItems = cartService.findCartItems(1L);

        List<Long> resultProductIds = cartItems.getCarts().stream()
                .map(CartItemResponse::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).containsExactly(1L, 2L);
    }

    @Test
    @DisplayName("장바구니 아이템의 수량을 업데이트한다.")
    void updateCartItemQuantity() {
        cartService.updateCartItemQuantity(1L, new CartRequest(1L, 7));

        CartItemsResponse cartItems = cartService.findCartItems(1L);
        List<Integer> quantities = cartItems.getCarts().stream()
                .map(CartItemResponse::getQuantity)
                .collect(Collectors.toList());
        assertThat(quantities).contains(7);
    }

    @Test
    @DisplayName("장바구니에서 아이템을 삭제한다.")
    void deleteCartItems() {
        cartService.deleteCartItems(1L, new ProductsRequest(List.of(1L)));

        CartItemsResponse cartItems = cartService.findCartItems(1L);
        List<Long> resultProductIds = cartItems.getCarts().stream()
                .map(CartItemResponse::getProductId)
                .collect(Collectors.toList());
        assertThat(resultProductIds).containsExactly(2L);
    }
}