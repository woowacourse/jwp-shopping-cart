package cart.domain.product;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void add(final Product product) {
        productDao.insert(product);
    }

    public void update(final Product product) {
        validateIdExist(product.getId());
        productDao.update(product);
    }

    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void deleteById(final Long id) {
        validateIdExist(id);
        productDao.deleteById(id);
    }

    public void validateIdExist(final Long id) {
        if (productDao.isExist(id)) {
            return;
        }
        throw new IllegalArgumentException("존재하지 않는 id입니다. value: " + id);
    }
}
