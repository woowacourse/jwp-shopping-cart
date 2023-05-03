package cart.service;

import cart.dao.ProductDao;
import cart.dto.ProductResponse;
import cart.entity.Product;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findProducts() {
        return productDao.findAll().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public void updateProduct(Product product) {
        productDao.update(product);
    }

    @Transactional
    public ProductResponse createProduct(Product product) {
        Product savedProduct = productDao.save(product);
        return ProductResponse.from(savedProduct);
    }

    @Transactional
    public void deleteProductBy(long id) {
        productDao.deleteById(id);
    }
}
