package cart.service;

import cart.dao.cart.CartDao;
import cart.domain.cart.Cart;
import cart.dto.cart.RequestCartDto;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void create(final Long memberId, final RequestCartDto requestCartDto) {
        final Cart cart = new Cart(requestCartDto.getProductId(), memberId);
        cartDao.insert(cart);
    }
}
