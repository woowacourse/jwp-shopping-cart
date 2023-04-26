package cart.service;

import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class ProductDeleteService {

    private final ProductRepository productRepository;

    public ProductDeleteService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void delete(final long id) {
        productRepository.deleteById(id);
    }
}
