package cart.service;

import cart.dao.CartsDao;
import cart.dao.ProductsDao;
import cart.entity.Product;
import cart.entity.vo.Email;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final CartsDao cartsDao;
    private final ProductsDao productsDao;

    public CartService(final CartsDao cartsDao, final ProductsDao productsDao) {
        this.cartsDao = cartsDao;
        this.productsDao = productsDao;
    }

    public long addProductToUser(final Email userEmail, final long productId) {
        final Product product = productsDao.findById(productId);
        return cartsDao.insert(userEmail, product);
    }
}
