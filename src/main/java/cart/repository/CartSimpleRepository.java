package cart.repository;

import cart.domain.Cart;
import cart.domain.CartItem;
import cart.domain.CartItems;
import cart.domain.Product;
import cart.domain.User;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CartSimpleRepository implements CartRepository {

    List<Cart> carts = Arrays.asList(
            new Cart(new User("rosie@wooteco.com", "1234"),
                    new CartItems(Arrays.asList(new CartItem(1L, Product.from(2L, "hi", "https://avatars.githubusercontent.com/u/61582017?v=4", 100000))))
            )
    );

    @Override
    public Cart findByUser(User user) {
        return carts.stream().filter(cart -> cart.getUser().equals(user)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디의 유저가 없어요"));
    }

    @Override
    public void removeCartItem(Cart cart, CartItem removalItem) {
        // TODO: removalItem 삭제하기
        Cart foundCart = carts.stream().filter(element -> element.equals(cart)).findAny()
                .orElseThrow(() -> new IllegalArgumentException());
        foundCart.removeCartItem(removalItem);
    }

    @Override
    public void addCartItem(Cart cart, CartItem cartItem) {
        // TODO: cartItem 생성하기
        Cart foundCart = carts.stream().filter(element -> element.equals(cart)).findAny()
                .orElseThrow(() -> new IllegalArgumentException());
        foundCart.addCartItem(cartItem);
    }
}
