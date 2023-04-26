package cart.service;

import cart.domain.Product;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateProductService {

    private final ProductRepository productRepository;

    public CreateProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(final String name, final String image, final int price) {
        return productRepository.save(new Product(name, image, price));
    }
}
