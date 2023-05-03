package cart.service;

import cart.dto.CartRequestDto;
import cart.entity.CartEntity;
import cart.repository.CartDao;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public int create(final CartRequestDto cartRequestDto, final int userId) {
        final CartEntity cart = new CartEntity(userId, cartRequestDto.getProductId());
        return cartDao.create(cart);
    }
}
