package cart.service;

import cart.dao.ProductDao;
import cart.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findAll();
    }

    public void updateProduct(Product product) {
        productDao.update(product);
    }

    public Product createProduct(Product product) {
        return productDao.save(product);
    }

    public void deleteProductBy(long id) {
        productDao.deleteById(id);
    }
}
