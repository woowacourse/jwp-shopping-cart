package cart.service;

import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductDeleteService {

    private final ProductRepository productRepository;

    public ProductDeleteService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void delete(final long id) {
        productRepository.deleteById(id);
    }
}
