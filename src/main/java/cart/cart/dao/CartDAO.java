package cart.cart.dao;

import cart.cart.domain.Cart;
import cart.cart.dto.CartRequestDTO;
import java.util.List;

public interface CartDAO {
    
    Cart create(CartRequestDTO cartRequestDTO);
    
    Cart find(CartRequestDTO cartRequestDTO);
    
    List<Cart> findUserCart(long userId);
    
    void delete(Cart cart);
    
    void clear(long userId);
}
