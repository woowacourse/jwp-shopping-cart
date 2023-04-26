package cart.business;

import cart.business.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductService {

    private final ProductRepository productRepository;

    public UpdateProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void perform(Product product) {
        productRepository.remove(product.getId());
        productRepository.insert(product);
    }
}
