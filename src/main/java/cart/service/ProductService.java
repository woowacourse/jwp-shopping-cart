package cart.service;

import cart.dao.ProductDao;
import cart.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<Product> findProducts() {
        return productDao.findAll();
    }

    @Transactional
    public void updateProduct(Product product) {
        productDao.update(product);
    }

    @Transactional
    public Product createProduct(Product product) {
        return productDao.save(product);
    }

    @Transactional
    public void deleteProductBy(long id) {
        productDao.deleteById(id);
    }
}
