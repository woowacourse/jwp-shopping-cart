package cart.domain.product.service;

import cart.dao.ProductDaoImpl;
import cart.dao.ProductEntity;
import cart.domain.product.ProductRepository;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.web.controller.dto.ProductRequest;
import cart.web.controller.dto.ProductResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    public final Logger log = LoggerFactory.getLogger(getClass());

    private final ProductRepository productRepository;

    public ProductService(final ProductDaoImpl productRepository) {
        this.productRepository = productRepository;
    }

    public List<ProductResponse> getProducts() {
        final List<ProductEntity> productEntities = productRepository.findAll();
        return productEntities.stream()
                .map(product -> new ProductResponse(product.getId(), product.getName(), product.getImageUrl(), product.getPrice(), product.getCategory()))
                .collect(Collectors.toList());
    }

    public Long save(final ProductRequest productRequest) {
        return productRepository.insert(productRequest.toEntity());
    }

    public void update(final Long id, final ProductRequest productRequest) {
        int updatedCount = productRepository.update(id, productRequest.toEntity());
        if (updatedCount != 1) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    public void delete(final Long id) {
        int deletedCount = productRepository.deleteById(id);
        if (deletedCount == 0) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        if (deletedCount > 1) {
            log.error("error = {}", "delete count is more than 2");
            throw new GlobalException(ErrorCode.INVALID_DELETE);
        }
    }

    public ProductRequest getById(final Long id) {
        final Optional<ProductEntity> productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        final ProductEntity productEntity = productById.get();
        return new ProductRequest(productEntity.getName(), productEntity.getImageUrl(),
                productEntity.getPrice(), productEntity.getCategory());
    }
}
