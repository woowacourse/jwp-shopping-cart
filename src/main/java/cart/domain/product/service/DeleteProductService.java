package cart.domain.product.service;

import cart.domain.product.ProductRepository;
import cart.domain.product.usecase.DeleteProductUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DeleteProductService implements DeleteProductUseCase {
    private final ProductRepository productRepository;

    public DeleteProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void delete(final Long id) {
        productRepository.deleteById(id);
    }
}
