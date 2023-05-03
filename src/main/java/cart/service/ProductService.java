package cart.service;

import cart.dao.ProductDao;
import cart.domain.product.Product;
import cart.domain.product.ProductEntity;
import java.util.List;
import javax.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductEntity> findAll() {
        return productDao.findAll();
    }

    @Transactional
    public ProductEntity register(@Valid final Product product) {
        final long id = productDao.insert(product);

        return new ProductEntity(id, product);
    }

    @Transactional
    public ProductEntity updateProduct(final long id, @Valid final Product product) {
        validateExistData(id);

        final ProductEntity newProduct = new ProductEntity(id, product);

        productDao.update(newProduct);

        return newProduct;
    }

    @Transactional
    public void deleteProduct(final long id) {
        validateExistData(id);
        productDao.delete(id);
    }

    private void validateExistData(final long id) {
        if (!productDao.isExist(id)) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}
