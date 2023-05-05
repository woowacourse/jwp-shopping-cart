package cart.service.cart;

import cart.controller.dto.request.MemberIdRequest;
import cart.dao.CarProductDao;
import cart.entity.CartProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartDeleteService {
    private final CarProductDao carProductDao;


    public CartDeleteService(final CarProductDao carProductDao) {
        this.carProductDao = carProductDao;
    }


    public void delete(final MemberIdRequest memberId, final Long productId) {
        final CartProductEntity cartProductEntity = new CartProductEntity(productId, memberId.getId());

        carProductDao.deleteById(cartProductEntity);
    }

}
