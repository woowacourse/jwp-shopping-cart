package cart.domain.product;

import cart.domain.product.dto.ProductCreationDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
public class AdminService {
    private final ProductRepository productRepository;

    public AdminService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public void save(ProductCreationDto productDto) {
        Product product = productDto.toProduct();

        productRepository.save(product);
    }
}
