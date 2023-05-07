package cart.domain.cart;

public interface CartRepository {

    Cart save(Cart cart);

    Cart getCartByMemberId(Long id);
}
