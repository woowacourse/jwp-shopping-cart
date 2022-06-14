package woowacourse.shoppingcart.domain.cart;

public interface CartItemRepository {
    CartItems findCartItemsByCustomer(long customerId);

    long addCartItem(long customerId, CartItem cartItem);

    CartItem findById(long id);

    void update(CartItem updateCartItem);

    void delete(CartItem deleteCartItem);
}
