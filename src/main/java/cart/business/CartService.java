package cart.business;

import cart.entity.Product;
import cart.persistence.CartDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CartService {

    private CartDao cartDao;

    public CartService(CartDao cartDao) {
        this.cartDao = cartDao;
    }

    public List<Product> findProductsByMemberId(Integer memberId) {
        return cartDao.findAllProductsByMemberId(memberId);
    }

    @Transactional
    public Integer delete(Integer id) {
        return cartDao.remove(id);
    }
}
