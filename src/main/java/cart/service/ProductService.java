package cart.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {
    public static final String PRODUCT_ID_NOT_EXIST_ERROR_MESSAGE = "존재하지 않는 상품 ID 입니다.";

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
            throw new IllegalArgumentException(PRODUCT_ID_NOT_EXIST_ERROR_MESSAGE);
        });
    }

    @Transactional(readOnly = true)
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Transactional
    public Long update(final Long id, final String name, final int price, final String image) {
        validateProductIdExist(id);

        final Product product = new Product(id, name, price, image);
        productDao.update(product);

        return id;
    }

    public void validateProductIdExist(final Long id) {
        if (!isProductIdExist(id)) {
            throw new IllegalArgumentException(PRODUCT_ID_NOT_EXIST_ERROR_MESSAGE);
        }
    }

    public boolean isProductIdExist(final Long id) {
        return productDao.isExist(id);
    }

    @Transactional
    public void delete(final Long id) {
        validateProductIdExist(id);
        productDao.deleteById(id);
    }
}
