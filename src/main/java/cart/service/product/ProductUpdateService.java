package cart.service.product;

import cart.entity.product.Product;
import cart.entity.product.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductUpdateService {

    private final ProductDao productDao;

    public ProductUpdateService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public void updateProduct(Product product) {
        productDao.update(product);
    }
}
