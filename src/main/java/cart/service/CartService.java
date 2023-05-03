package cart.service;

import cart.annotation.ServiceWithTransactionalReadOnly;
import cart.controller.dto.request.ProductIdRequest;
import cart.dao.CartDao;
import org.springframework.transaction.annotation.Transactional;

@ServiceWithTransactionalReadOnly
public class CartService {

    private final CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    @Transactional
    public void save(final Long memberId, final ProductIdRequest request) {
        cartDao.create(memberId, request.getProductId());
    }

}
