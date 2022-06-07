package woowacourse.shoppingcart.infra;

import java.util.List;
import woowacourse.shoppingcart.domain.Carts;

public interface CartRepository {
    Carts findCartsByMemberId(long memberId);

    void saveCarts(Carts carts);

    void deleteByCartIds(List<Long> cartIds);
}
