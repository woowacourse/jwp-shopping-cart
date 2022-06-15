package woowacourse.shoppingcart.infra;

import java.util.List;
import woowacourse.shoppingcart.domain.Carts;

public interface CartRepository {
    Carts findCartsByCustomerId(long memberId);

    void saveCarts(Carts carts);

    void deleteCartItemsByProductIds(List<Long> cartIds);
}
