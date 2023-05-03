package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import org.springframework.stereotype.Service;

@Service
public class CartService {
    private final CartDao cartDao;
    private final ProductDao productDao;

    public CartService(CartDao cartDao, ProductDao productDao) {
        this.cartDao = cartDao;
        this.productDao = productDao;
    }

    public void addProduct(int memberId, int productId) {
        cartDao.save(memberId, productId);
    }
}
