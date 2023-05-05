package cart.service.cart;

import cart.controller.dto.request.MemberIdRequest;
import cart.dao.CartDao;
import cart.entity.CartEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartDeleteService {
    private final CartDao cartDao;


    public CartDeleteService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }


    public void delete(final MemberIdRequest memberId, final Long productId) {
        final CartEntity cartEntity = new CartEntity(productId, memberId.getId());

        cartDao.deleteById(cartEntity);
    }

}
