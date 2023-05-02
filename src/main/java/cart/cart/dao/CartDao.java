package cart.cart.dao;

import cart.cart.domain.Cart;

import java.util.List;

public interface CartDao {
    Long save(final Long productId, final Long memberId);
    
    void deleteAll();
    
    List<Cart> findByMemberId(final Long memberId);
    
    void deleteByCartIdAndMemberId(final Long cartId, final Long memberId);
    
    void deleteByProductId(final Long productId);
}
