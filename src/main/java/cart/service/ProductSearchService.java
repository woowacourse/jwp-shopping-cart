package cart.service;

import java.util.List;

import cart.domain.product.Product;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductSearchService {

    private final ProductRepository productRepository;

    public ProductSearchService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }
}
