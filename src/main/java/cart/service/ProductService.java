package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void saveProduct(Product product) {
        productDao.save(product);
    }

    public List<Product> findAllProducts() {
        return productDao.findAllProducts();
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    public void deleteProduct(long productId) {
        productDao.deleteProduct(productId);
    }
}
