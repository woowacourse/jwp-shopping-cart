package cart.domain.cart;

public interface CartRepository {

    void create(long userId);

    Cart findByUserId(long userId);
}
