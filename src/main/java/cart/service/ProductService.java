package cart.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Long save(final String name, final int price, final String image) {
        final Product product = new Product(name, price, image);
        return productDao.insert(product);
    }

    @Transactional(readOnly = true)
    public Product findById(final Long id) {
        return productDao.findById(id).orElseThrow(() -> {
            throw new IllegalArgumentException("존재하지 않는 상품 ID 입니다.");
        });
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Transactional
    public void update(final Long id, final String name, final int price, final String image) {
        validateExistProductId(id);
        final Product product = new Product(id, name, price, image);
        productDao.update(product);
    }

    public void validateExistProductId(final Long id) {
        if (productDao.findById(id).isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 상품 id 입니다.");
        }
    }

    @Transactional
    public void delete(final Long id) {
        validateExistProductId(id);
        productDao.deleteById(id);
    }
}
