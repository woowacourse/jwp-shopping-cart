package cart.factory;

import static cart.factory.ProductFactory.createOtherProduct;
import static cart.factory.ProductFactory.createProduct;

import cart.domain.CartItems;
import cart.domain.CartItem;
import java.util.List;

public class CartFactory {
    public static CartItems createCart() {
        List<CartItem> cartItems = List.of(new CartItem(createProduct()), new CartItem(createOtherProduct()));
        return new CartItems(cartItems);
    }
}
