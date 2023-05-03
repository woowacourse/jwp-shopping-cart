package cart.factory.cart;

import cart.domain.cart.CartItems;
import cart.domain.product.Product;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static cart.factory.product.ProductFactory.createProduct;

public class CartItemsFactory {

    public static CartItems createCartItems() {
        List<Product> cartItems = new ArrayList<>();
        cartItems.add(createProduct());
        return new CartItems(cartItems);
    }

    public static CartItems createCartItems(final Product product) {
        List<Product> cartItems = new ArrayList<>();
        cartItems.add(product);
        return new CartItems(cartItems);
    }

    public static CartItems createCartItems(Product... product) {
        List<Product> cartItems = new ArrayList<>(Arrays.asList(product));
        return new CartItems(cartItems);
    }
}
