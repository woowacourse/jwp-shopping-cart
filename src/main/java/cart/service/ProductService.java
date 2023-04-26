package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
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

    public void save(final String name, final int price, final String image) {
        Product product = new Product(name, price, image);
        productDao.insert(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productDao.findAll();
    }

    public void update(final Long id, final String name, final int price, final String image) {
        checkExistProductId(id);
        Product product = new Product(id, name, price, image);
        productDao.update(product);
    }

    private void checkExistProductId(Long id) {
        if (productDao.findById(id) == null) {
            throw new IllegalArgumentException("존재하지 않는 상품 id 입니다.");
        }
    }

    public void delete(final Long id) {
        checkExistProductId(id);
        productDao.deleteById(id);
    }
}
