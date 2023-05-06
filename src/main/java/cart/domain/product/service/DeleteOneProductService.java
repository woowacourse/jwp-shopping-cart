package cart.domain.product.service;

import cart.domain.product.ProductRepository;
import cart.domain.product.usecase.DeleteOneProductUseCase;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class DeleteOneProductService implements DeleteOneProductUseCase {
    private final ProductRepository productRepository;

    public DeleteOneProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void deleteSingleProductById(final Long id) {
        productRepository.deleteById(id);
    }
}
