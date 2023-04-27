package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import java.util.List;

import cart.dto.request.ProductSaveRequest;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public long saveProduct(ProductSaveRequest productSaveRequest) {
        return productDao.save(productSaveRequest);
    }

    public List<Product> findAllProducts() {
        return productDao.findAllProducts();
    }

    public void updateProduct(Product product) {
        productDao.updateProduct(product);
    }

    public void deleteProduct(long productId) {
        productDao.deleteProduct(productId);
    }
}
