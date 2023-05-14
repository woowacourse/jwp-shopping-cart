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
        productDao.save(toEntity(request));
    }

    public void update(Long id, ProductRequest request) {
        productDao.update(id, toEntity(request));
    }

    public void delete(Long id) {
        productDao.deleteById(id);
    }

    private Product toEntity(ProductRequest request) {
        return new Product(request.getName(), request.getImageUrl(), request.getPrice());
    }
}
