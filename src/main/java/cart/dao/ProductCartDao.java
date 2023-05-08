package cart.dao;

import cart.entity.ProductCart;
import java.util.List;
import java.util.Optional;

public interface ProductCartDao {

    ProductCart save(ProductCart productCart);

    Optional<ProductCart> findById(Long id);

    List<ProductCart> findAllByMemberId(Long memberId);

    void deleteByIdAndMemberId(Long cartId, Long memberId);

    boolean existByCartIdAndMemberId(Long cartId, Long memberId);
}
