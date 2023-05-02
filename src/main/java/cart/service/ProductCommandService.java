package cart.service;

import cart.domain.product.Product;
import cart.repository.product.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ProductCommandService {

    private final ProductRepository productRepository;

    public ProductCommandService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void delete(final long id) {
        productRepository.deleteById(id);
    }

    public Product create(final String name, final String image, final int price) {
        return productRepository.save(new Product(name, image, price));
    }

    public Product update(final long productId, final String name, final String image, final int price) {
        return productRepository.update(new Product(productId, name, image, price));
    }
}
