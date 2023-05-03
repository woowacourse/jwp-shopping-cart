package cart.dao;

import cart.entity.CartItemEntity;
import cart.entity.ProductEntity;

import java.util.List;

public interface CartItemDao {

    int addCartItem(CartItemEntity cartItemEntity);

    List<ProductEntity> selectAllCartItems(int memberId);

    void removeCartItem(int cartItemId);
}
