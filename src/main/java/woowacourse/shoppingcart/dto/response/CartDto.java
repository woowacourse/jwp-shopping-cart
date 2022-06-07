package woowacourse.shoppingcart.dto.response;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import woowacourse.shoppingcart.domain.Cart;

public class CartDto {

    private List<CartItemDto> cartItems;

    public CartDto() {
    }

    public CartDto(Cart cart) {
        this.cartItems = cart.getCartItems()
                .stream()
                .map(CartItemDto::new)
                .collect(Collectors.toList());
    }

    public List<CartItemDto> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItemDto> cartItems) {
        this.cartItems = cartItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CartDto cartDto = (CartDto) o;
        return Objects.equals(cartItems, cartDto.cartItems);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cartItems);
    }

    @Override
    public String toString() {
        return "CartDto{" + "cartItems=" + cartItems + '}';
    }
}
