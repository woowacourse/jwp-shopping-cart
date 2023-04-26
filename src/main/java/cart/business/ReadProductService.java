package cart.business;

import cart.business.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReadProductService {

    private final ProductRepository productRepository;

    public ReadProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional(readOnly = true)
    public List<Product> perform() {
        return productRepository.findAll();
    }
}
