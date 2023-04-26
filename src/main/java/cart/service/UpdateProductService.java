package cart.service;

import cart.domain.Product;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateProductService {

    private final ProductRepository productRepository;

    public UpdateProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product update(final long productId, final String name, final String image, final int price) {
        return productRepository.update(new Product(productId, name, image, price));
    }
}
