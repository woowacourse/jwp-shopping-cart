package cart.dao.cart;

import cart.domain.product.Product;

import java.util.List;

public interface CartDao {

    void insert(final Long memberId, final Long productId);

    List<Product> findAllProductByMemberId(final Long memberId);

    void deleteByMemberIdAndProductId(final Long memberId, final Long productId);
}
