package cart.service;

import cart.entity.Product;
import cart.repository.ProductRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public long createProduct(final Product product) {
        final Product savedProduct = productRepository.save(product);
        return savedProduct.getId();
    }

    public void updateProduct(final Product product) {
        productRepository.update(product);
    }

    public void deleteById(final long id) {
        productRepository.deleteById(id);
    }
}
