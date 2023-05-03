package cart.service;

import cart.annotation.ServiceWithTransactionalReadOnly;
import cart.controller.dto.request.MemberRequest;
import cart.controller.dto.request.ProductIdRequest;
import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.entity.ProductEntity;
import cart.exception.ProductNotFoundException;
import org.springframework.transaction.annotation.Transactional;

@ServiceWithTransactionalReadOnly
public class CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(final CartDao cartDao, final ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    @Transactional
    public void save(final Long memberId, final ProductIdRequest request) {
        cartDao.create(memberId, request.getProductId());
    }

}
