package cart.service;

import cart.dao.CartDao;
import cart.service.dto.CartRequest;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartDao cartDao;

    public CartService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public long save(final CartRequest cartRequest, final long customerId) {
        return cartDao.insert(customerId, cartRequest.getProductId());
    }

}
