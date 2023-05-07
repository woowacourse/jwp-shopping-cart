package cart.domain.cart;

import java.util.ArrayList;
import java.util.List;

public class InMemoryCartRepository implements CartRepository {

    private final List<Cart> carts;

    public InMemoryCartRepository() {
        this.carts = new ArrayList<>();
    }

    @Override
    public void create(long userId) {
        this.carts.add(Cart.createEmpty(userId));
    }

    @Override
    public Cart findByUserId(long userId) {
        return this.carts.stream()
                .filter(cart -> cart.getMemberId() == userId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("해당 사용자의 장바구니가 존재하지 않습니다." + System.lineSeparator() +
                        "userId: " + userId));
    }

    @Override
    public void update(Cart cart) {
        this.carts.remove(cart);
        this.carts.add(cart);
    }
}
