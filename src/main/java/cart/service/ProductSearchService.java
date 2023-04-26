package cart.service;

import java.util.List;

import cart.domain.Product;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductSearchService {


    private final ProductRepository productRepository;

    public ProductSearchService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> find() {
        return productRepository.find();
    }
}
