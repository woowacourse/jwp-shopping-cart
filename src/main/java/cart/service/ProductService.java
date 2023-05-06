package cart.service;

import cart.entity.Product;
import cart.exception.ProductNotFoundException;
import cart.repository.ProductRepository;
import cart.service.dto.ProductRequest;
import cart.service.dto.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> findAll() {
        return productRepository.findAll().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }

    @Transactional
    public long createProduct(final ProductRequest request) {
        final Product savedProduct = productRepository.save(request.toProduct());
        return savedProduct.getId();
    }

    @Transactional
    public void updateProduct(final Long id, final ProductRequest productRequest) {
        final int updatedCount = productRepository.update(productRequest.toProduct(id));
        validateProductNotFound(updatedCount);
    }

    private static void validateProductNotFound(final int updatedCount) {
        if (updatedCount == 0) {
            throw new ProductNotFoundException();
        }
    }

    @Transactional
    public void deleteById(final long id) {
        productRepository.deleteById(id);
    }
}
