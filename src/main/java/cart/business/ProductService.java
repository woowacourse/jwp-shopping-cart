package cart.business;

import cart.entity.ProductEntity;
import cart.persistence.ProductDao;
import cart.presentation.dto.ProductRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    private ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Integer create(ProductRequest request) {
        ProductEntity product = makeProductFromRequest(request);
        productDao.findSameProductExist(product);

        return productDao.insert(product);
    }

    @Transactional(readOnly = true)
    public List<ProductEntity> read() {
        return productDao.findAll();
    }

    @Transactional
    public Integer update(Integer id, ProductRequest request) {
        ProductEntity product = makeProductFromRequest(request);
        return productDao.update(id, product);
    }

    @Transactional
    public Integer delete(Integer id) {
        return productDao.remove(id);
    }

    private ProductEntity makeProductFromRequest(ProductRequest request) {
        return new ProductEntity(null, request.getName(), request.getUrl(), request.getPrice());
    }
}
