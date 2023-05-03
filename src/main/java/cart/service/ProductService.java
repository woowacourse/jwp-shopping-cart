package cart.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.exception.notfound.ProductNotFoundException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public void save(final String name, final int price, final String imageUrl) {
        Product product = new Product(name, price, imageUrl);
        productDao.insert(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void update(final Long id, final String name, final int price, final String imageUrl) {
        checkExistProductId(id);
        Product product = new Product(id, name, price, imageUrl);
        productDao.update(product);
    }

    private void checkExistProductId(Long id) {
        if (productDao.findById(id).isEmpty()) {
            throw new ProductNotFoundException();
        }
    }

    public void delete(final Long id) {
        checkExistProductId(id);
        productDao.deleteById(id);
    }
}
