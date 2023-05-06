package shoppingbasket.cart.dao;

import shoppingbasket.cart.entity.CartEntity;
import shoppingbasket.cart.entity.CartProductEntity;
import java.util.List;

public interface CartDao {

    CartEntity insert(int memberId, int productId);

    CartEntity findById(int cartId);

    List<CartProductEntity> selectAllCartProductByMemberId(int memberId);

    void delete(int cartId);

    CartProductEntity selectCartById(int cartId);
}
