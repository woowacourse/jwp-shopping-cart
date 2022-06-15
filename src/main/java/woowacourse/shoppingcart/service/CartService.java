package woowacourse.shoppingcart.service;

import java.util.List;
import woowacourse.shoppingcart.domain.Carts;

public interface CartService {
    void add(final long memberId, final long productId);

    Carts findCartsByCustomerId(long id);

    void updateQuantity(long id, long productId, int quantity);

    void deleteByCartIds(long customerId, List<Long> cartIds);
}
