package cart.domain.persistence.dao;

import java.util.List;

import cart.domain.persistence.ProductDto;
import cart.domain.persistence.entity.CartEntity;

public interface CartDao {

    long save(CartEntity cartEntity);

    List<ProductDto> findAllByMemberId(Long memberId);

    int deleteByCartId(long cartId);

    int deleteByProductId(long productId);
}
