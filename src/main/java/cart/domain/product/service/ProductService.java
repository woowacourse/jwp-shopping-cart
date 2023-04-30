package cart.domain.product.service;

import cart.domain.product.dto.ProductCreateRequest;
import cart.domain.product.dto.ProductResponse;
import cart.domain.product.dto.ProductUpdateRequest;
import cart.domain.product.entity.Product;
import cart.domain.product.repository.ProductRepository;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(final ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponse create(final ProductCreateRequest productCreateRequest) {
        final Product product = productCreateRequest.makeProduct();
        final Product savedProduct = productRepository.save(product);
        return ProductResponse.of(savedProduct);
    }

    public List<ProductResponse> findAll() {
        final List<Product> products = productRepository.findAll();
        return products.stream()
            .map(ProductResponse::of)
            .collect(Collectors.toUnmodifiableList());
    }

    public void update(final ProductUpdateRequest productUpdateRequest) {
        final int count = productRepository.update(productUpdateRequest.makeProduct());
        checkProductExist(count);
    }

    public void delete(final Long id) {
        final int count = productRepository.delete(id);
        checkProductExist(count);
    }

    private void checkProductExist(final int count) {
        if (count == 0) {
            throw new IllegalArgumentException("존재하지 않는 아이템입니다.");
        }
    }
}
