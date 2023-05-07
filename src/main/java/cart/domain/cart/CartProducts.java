package cart.domain.cart;

import cart.domain.product.ProductId;
import cart.exception.AlreadyAddedProductException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CartProducts {

    private final List<CartProduct> cartProducts;

    public CartProducts() {
        this(new ArrayList<>());
    }

    public CartProducts(final List<CartProduct> cartProducts) {
        this.cartProducts = cartProducts;
    }

    public void add(final ProductId productId) {
        if (cartProducts.stream().anyMatch(cartProduct -> cartProduct.getProductId().equals(productId))) {
            throw new AlreadyAddedProductException("이미 장바구니에 담긴 상품입니다.");
        }
        cartProducts.add(new CartProduct(productId));
    }

    public void delete(final Long cartProductId) {
        cartProducts.removeIf(cartProduct -> cartProduct.getCartProductId().getValue().equals(cartProductId));
    }

    public List<CartProduct> getCartProducts() {
        return cartProducts;
    }

    public List<Long> getProductIds() {
        return cartProducts.stream()
                .map(cartProduct -> cartProduct.getProductId().getValue())
                .collect(Collectors.toList());
    }
}
