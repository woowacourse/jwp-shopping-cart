package cart.factory;

import static cart.factory.ProductFactory.createOtherProduct;
import static cart.factory.ProductFactory.createProduct;

import cart.domain.CartProduct;
import cart.domain.CartProducts;
import java.util.List;

public class CartProductsFactory {
    public static CartProducts createCart() {
        List<CartProduct> cartProducts = List.of(new CartProduct(createProduct()), new CartProduct(createOtherProduct()));
        return new CartProducts(cartProducts);
    }
}
