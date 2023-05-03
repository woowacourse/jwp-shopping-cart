package cart.service.product;

import cart.entity.product.Product;
import cart.entity.product.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductFindService {
    private final ProductDao productDao;

    public ProductFindService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findAll();
    }
}
