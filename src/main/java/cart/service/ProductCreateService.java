package cart.service;

import cart.entity.Product;
import cart.entity.dao.ProductDao;
import org.springframework.stereotype.Service;

@Service
public class ProductCreateService {

    private final ProductDao productDao;

    public ProductCreateService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Product createProduct(Product product) {
        return productDao.save(product);
    }
}
