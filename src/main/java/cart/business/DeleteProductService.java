package cart.business;

import cart.business.domain.Product;
import org.springframework.stereotype.Service;

@Service
public class DeleteProductService {

    private final ProductRepository productRepository;

    public DeleteProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void perform(Product product) {
        productRepository.remove(product.getId());
    }
}
