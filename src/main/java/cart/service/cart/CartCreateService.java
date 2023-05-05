package cart.service.cart;

import cart.controller.dto.request.CartItemCreationRequest;
import cart.controller.dto.request.MemberIdRequest;
import cart.dao.CarProductDao;
import cart.entity.CartProductEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CartCreateService {
    private final CarProductDao carProductDao;


    public CartCreateService(final CarProductDao carProductDao) {
        this.carProductDao = carProductDao;
    }

    public void addProduct(final CartItemCreationRequest productId, final MemberIdRequest member) {
        final CartProductEntity cartProductEntity = new CartProductEntity(productId.getProductId(), member.getId());

        carProductDao.save(cartProductEntity);
    }
}
