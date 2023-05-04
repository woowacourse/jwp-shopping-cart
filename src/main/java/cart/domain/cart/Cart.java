package cart.domain.cart;

import cart.domain.member.Member;
import cart.domain.product.Product;
import cart.exception.CartItemDuplicatedException;

public class Cart {

    private final Long id;
    private final Member member;
    private final CartItems cartItems;

    public Cart(final Long id, final Member member, final CartItems cartItems) {
        this.id = id;
        this.member = member;
        this.cartItems = cartItems;
    }

    public static Cart from(final Member member, final CartItems cartItems) {
        return new Cart(null, member, cartItems);
    }

    public static Cart createEmptyCart(final Member member) {
        return new Cart(null, member, null);
    }

    public void addCartItem(final Product product) {
        checkIsDuplicatedCartItem(product);
        this.cartItems.add(product);
    }

    private void checkIsDuplicatedCartItem(final Product product) {
        if (containsCartItem(product)) {
            throw new CartItemDuplicatedException();
        }
    }

    public Product removeCartItem(final Product product) {
        return this.cartItems.remove(product);
    }

    public boolean containsCartItem(final Product product) {
        return this.cartItems.contains(product);
    }

    public Long getId() {
        return id;
    }

    public Member getMember() {
        return member;
    }

    public CartItems getCartItems() {
        return cartItems;
    }

    public Product getLastCartItem() {
        return this.cartItems.getLastCartItem();
    }
}
