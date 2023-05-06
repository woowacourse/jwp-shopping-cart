package cart.service;

import cart.domain.Product;
import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.persistence.dao.ProductDao;
import cart.persistence.entity.ProductEntity;
import cart.service.dto.ProductRequest;
import cart.service.dto.ProductResponse;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<ProductResponse> getProducts() {
        final List<ProductEntity> productEntities = productDao.findAll();
        return productEntities.stream()
            .map(this::convertToDto).collect(Collectors.toList());
    }

    @Transactional
    public long save(final ProductRequest productRequest) {
        final ProductEntity productEntity = convertToEntity(productRequest);
        return productDao.insert(productEntity);
    }

    @Transactional
    public void update(final Long id, final ProductRequest productRequest) {
        final ProductEntity productEntity = convertToEntity(productRequest);
        int updatedCount = productDao.updateById(productEntity, id);
        if (updatedCount != 1) {
            throw new GlobalException(ErrorCode.PRODUCT_INVALID_UPDATE);
        }
    }

    @Transactional
    public void delete(final Long id) {
        int deletedCount = productDao.deleteById(id);
        if (deletedCount != 1) {
            throw new GlobalException(ErrorCode.PRODUCT_INVALID_DELETE);
        }
    }

    public ProductResponse getById(final Long id) {
        return productDao.findById(id)
            .map(this::convertToDto)
            .orElseThrow(() -> new GlobalException(ErrorCode.PRODUCT_NOT_FOUND));
    }

    private ProductEntity convertToEntity(final ProductRequest productRequest) {
        final Product product = Product.create(productRequest.getName(), productRequest.getImageUrl(),
            productRequest.getPrice(), productRequest.getCategory());
        return new ProductEntity(product.getName(), product.getImageUrl(), product.getPrice(),
            product.getCategory().name());
    }

    private ProductResponse convertToDto(final ProductEntity product) {
        return new ProductResponse(product.getId(), product.getName(), product.getImageUrl(),
            product.getPrice(), product.getCategory());
    }
}
