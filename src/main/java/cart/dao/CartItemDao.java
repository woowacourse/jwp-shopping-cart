package cart.dao;

import cart.entity.CartItemEntity;
import cart.entity.ProductEntity;

import javax.validation.Valid;
import java.util.List;

public interface CartItemDao {

    List<CartItemEntity> selectAllByMemberId(final Long memberId);

    long insert(final CartItemEntity cartItemEntity);

    int deleteById(final long id);

}
