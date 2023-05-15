package cart.dao;

import cart.domain.cart.CartProduct;
import cart.domain.member.Member;
import cart.domain.product.Product;
import java.util.List;

public interface CartProductDao {

    Long saveAndGetId(final CartProduct cartProduct);

    List<Product> findAllProductByMember(final Member member);

    int delete(final Long productId, final Member member);
}
