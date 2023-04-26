package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequest;
import cart.entity.ProductEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void addProduct(final ProductRequest productRequest) {
        final ProductEntity productEntity = new ProductEntity(
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice());
        productDao.save(productEntity);
    }

    public void updateProduct(final long id, final ProductRequest productRequest) {
        final ProductEntity productEntity = new ProductEntity(
                id,
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice());
        productDao.update(productEntity);
    }

    public void deleteProduct(final long id) {
        productDao.delete(id);
    }
}
