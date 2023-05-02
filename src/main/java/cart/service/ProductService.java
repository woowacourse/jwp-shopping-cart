package cart.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public void save(final String name, final int price, final String image) {
        Product product = new Product(name, price, image);
        productDao.insert(product);
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Transactional
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

    @Transactional
    public void delete(final Long id) {
        checkExistProductId(id);
        productDao.deleteById(id);
    }
}
