package cart.domain.cart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryCartRepository implements CartRepository {

    private final Map<Long, Long> userCartMap;
    private final List<Cart> carts;
    private long serial;

    public InMemoryCartRepository() {
        this.userCartMap = new HashMap<>();
        this.carts = new ArrayList<>();
        this.serial = 0;
    }

    @Override
    public void create(long userId) {
        long cartId = getSerial();
        this.carts.add(Cart.createEmpty(cartId));
        this.userCartMap.put(userId, cartId);
    }

    @Override
    public Cart findByUserId(long userId) {
        Long cartId = this.userCartMap.get(userId);
        return this.carts.stream()
                .filter(cart -> cart.getId() == cartId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("해당 사용자의 장바구니가 존재하지 않습니다." + System.lineSeparator() +
                        "userId: " + userId));
    }

    @Override
    public void update(Cart cart) {
        this.carts.remove(cart);
        this.carts.add(cart);
    }

    private long getSerial() {
        this.serial += 1;
        return this.serial;
    }
}
