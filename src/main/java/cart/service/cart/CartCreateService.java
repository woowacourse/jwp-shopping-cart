package cart.service.cart;

import cart.controller.dto.request.CartItemCreationRequest;
import cart.controller.dto.request.MemberIdRequest;
import cart.dao.CartDao;
import cart.entity.CartEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartCreateService {
    private final CartDao cartDao;


    public CartCreateService(final CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public void addProduct(final CartItemCreationRequest productId, final MemberIdRequest member) {
        final CartEntity cartEntity = new CartEntity(productId.getProductId(), member.getId());

        cartDao.save(cartEntity);
    }
}
