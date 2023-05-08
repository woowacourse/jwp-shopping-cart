package cart.business;

import cart.entity.Product;
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
        Product product = makeProductFromRequest(request);
        productDao.findSameProductExist(product);

        return productDao.insert(product);
    }

    @Transactional(readOnly = true)
    public List<Product> read() {
        return productDao.findAll();
    }

    @Transactional
    public Integer update(Integer id, ProductRequest request) {
        Product product = makeProductFromRequest(request);
        return productDao.update(id, product);
    }

    @Transactional
    public Integer delete(Integer id) {
        return productDao.remove(id);
    }

    private Product makeProductFromRequest(ProductRequest request) {
        return new Product(null, request.getName(), request.getUrl(), request.getPrice());
    }
}
