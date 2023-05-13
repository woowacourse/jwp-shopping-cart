package cart.business;

import cart.entity.ProductEntity;
import cart.persistence.CartDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private CartDao cartDao;
    private CartProductService cartProductService;

    public CartService(CartDao cartDao, CartProductService cartProductService) {
        this.cartDao = cartDao;
        this.cartProductService = cartProductService;
    }

    public List<ProductEntity> findProductsByMemberId(Integer memberId) {
        return cartDao.findAllProductsByMemberId(memberId);
    }

    @Transactional
    public Integer delete(Integer id) {
        return cartDao.remove(id);
    }
}
