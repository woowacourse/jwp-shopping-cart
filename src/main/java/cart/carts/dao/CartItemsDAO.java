package cart.carts.dao;

import cart.carts.domain.CartItem;
import java.util.List;

public interface CartItemsDAO {
    
    CartItem create(long productId);
    
    void delete(CartItem item);
    
    CartItem findById(long cartId);
    
    List<CartItem> findAll();
}
