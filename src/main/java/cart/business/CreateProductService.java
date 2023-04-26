package cart.business;

import cart.business.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class CreateProductService {

    private final ProductRepository productRepository;

    public CreateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void perform(Product product) {
        productRepository.insert(product);
    }
}
