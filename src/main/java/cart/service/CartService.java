package cart.service;

import cart.controller.dto.CartRequest;
import cart.dao.CartDao;
import cart.domain.Cart;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public Long saveCart(final CartRequest cartRequest) {
        Cart cart = new Cart.Builder()
                .userId(cartRequest.getUserId())
                .itemId(cartRequest.getItemId())
                .build();
        return cartDao.save(cart);
    }
}
