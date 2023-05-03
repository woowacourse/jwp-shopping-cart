package cart.service;

import cart.dao.entity.Product;
import cart.dto.ProductResponse;
import cart.dto.ProductSaveRequest;
import cart.dto.ProductUpdateRequest;
import cart.respository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    public Long save(final ProductSaveRequest productSaveRequest) {
        final Product product = productMapper.mapFrom(productSaveRequest);
        return productRepository.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        final List<Product> products = productRepository.findAll();
        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public void delete(final Long id) {
        productRepository.delete(id);
    }

    public void update(final Long id, final ProductUpdateRequest request) {
        productRepository.update(id, productMapper.mapFrom(request));
    }
}
