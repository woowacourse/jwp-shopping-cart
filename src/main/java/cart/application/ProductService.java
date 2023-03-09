package cart.application;

import cart.domain.Product;
import cart.dao.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    public Product getProductById(Long productId) {
        return productDao.getProductById(productId);
    }

    public Long createProduct(Product product) {
        return productDao.createProduct(product);
    }

    public void updateProduct(Long productId, Product product) {
        productDao.updateProduct(productId, product);
    }

    public void deleteProduct(Long productId) {
        productDao.deleteProduct(productId);
    }
}