package cart.service;

import cart.domain.product.Product;
import cart.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductCreateService {

    private final ProductRepository productRepository;

    public ProductCreateService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product create(final String name, final String image, final int price) {
        return productRepository.save(new Product(name, image, price));
    }
}
