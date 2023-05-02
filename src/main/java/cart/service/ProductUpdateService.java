package cart.service;

import cart.entity.Product;
import cart.entity.dao.ProductDao;
import org.springframework.stereotype.Service;

@Service
public class ProductUpdateService {

    private final ProductDao productDao;

    public ProductUpdateService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void updateProduct(Product product) {
        productDao.update(product);
    }
}
