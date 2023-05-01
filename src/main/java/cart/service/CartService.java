package cart.service;

import cart.dao.CartDao;
import cart.dto.CartSaveRequest;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;
    private final CartMapper cartMapper;

    public CartService(final CartDao cartDao, final CartMapper cartMapper) {
        this.cartDao = cartDao;
        this.cartMapper = cartMapper;
    }

    public Long save(final CartSaveRequest saveRequest) {
        return cartDao.save(cartMapper.mapFrom(saveRequest));
    }
}
