package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductRequest;
import cart.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public void addProduct(final ProductRequest productRequest) {
        final Product product = new Product(
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice());
        productDao.save(product);
    }

    public void updateProduct(final long id, final ProductRequest productRequest) {
        final Product product = new Product(
                id,
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice());
        productDao.update(product);
    }

    public void deleteProduct(final long id) {
        productDao.delete(id);
    }
}
