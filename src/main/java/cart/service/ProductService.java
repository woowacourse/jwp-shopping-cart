package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public void add(final Product product) {
        productDao.insert(product);
    }

    @Transactional
    public void update(final Product product) {
        validateIdExist(product.getId());
        productDao.update(product);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Transactional
    public void deleteById(final Long id) {
        validateIdExist(id);
        productDao.deleteById(id);
    }

    private void validateIdExist(final Long id) {
        if (productDao.isExist(id)) {
            return;
        }
        throw new IllegalArgumentException("존재하지 않는 id입니다. value: " + id);
    }
}
