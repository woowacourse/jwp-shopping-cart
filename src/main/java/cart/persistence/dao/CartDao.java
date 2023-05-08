package cart.persistence.dao;

import java.util.List;

import cart.persistence.CartProduct;
import cart.persistence.entity.Cart;

public interface CartDao {

    long save(Cart cart);

    List<CartProduct> findAllByMemberId(Long memberId);

    int deleteByCartId(long cartId);

    int deleteByProductId(long productId);
}
