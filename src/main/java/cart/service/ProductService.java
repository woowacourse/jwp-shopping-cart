package cart.service;

import cart.controller.dto.ProductRequest;
import cart.dao.ProductDao;
import cart.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void save(ProductRequest request) {
        productDao.save(toProduct(request));
    }

    public void update(Long id, ProductRequest request) {
        productDao.update(id, toProduct(request));
    }

    public void delete(Long id) {
        productDao.deleteById(id);
    }

    private Product toProduct(ProductRequest request) {
        return new Product(request.getName(), request.getImageUrl(), request.getPrice());
    }
}
