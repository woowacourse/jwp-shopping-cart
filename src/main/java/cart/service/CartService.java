package cart.service;

import cart.dao.CartDao;
import cart.dto.CartDto;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void save(CartDto cartDto) {
        cartDao.save(cartDto.toEntity());
    }

}
