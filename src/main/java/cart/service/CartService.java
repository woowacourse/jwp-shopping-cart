package cart.service;

import cart.dao.cartAddedProductDao;
import cart.dao.ProductsDao;
import cart.entity.Product;
import cart.entity.vo.Email;
import org.springframework.stereotype.Service;

@Service
public class CartService {

    private final cartAddedProductDao cartAddedProductDao;
    private final ProductsDao productsDao;

    public CartService(final cartAddedProductDao cartAddedProductDao, final ProductsDao productsDao) {
        this.cartAddedProductDao = cartAddedProductDao;
        this.productsDao = productsDao;
    }

    public long addProductToUser(final Email userEmail, final long productId) {
        final Product product = productsDao.findById(productId);
        return cartAddedProductDao.insert(userEmail, product);
    }
}
