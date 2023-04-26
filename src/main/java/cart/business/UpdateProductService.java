package cart.business;

import cart.business.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UpdateProductService {

    private final CreateProductService createProductService;
    private final ProductRepository productRepository;

    public UpdateProductService(CreateProductService createProductService, ProductRepository productRepository) {
        this.createProductService = createProductService;
        this.productRepository = productRepository;
    }

    @Transactional
    public void perform(Product product) {
        productRepository.remove(product.getId());
        createProductService.perform(product);
    }
}
