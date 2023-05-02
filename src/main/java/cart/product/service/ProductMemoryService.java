package cart.product.service;

import cart.product.dao.ProductDao;
import cart.product.domain.Product;
import cart.product.dto.ProductRequest;
import cart.product.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional
@Service
public class ProductMemoryService implements ProductService {
    private final ProductDao productDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findByProductIds(final List<Long> productIds) {
        return productIds.stream()
                .map(productDao::findById)
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productDao.findAll().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
    
    @Override
    public Long save(final ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        return productDao.save(product);
    }
    
    @Override
    public void update(final Long id, final ProductRequest productRequest) {
        validateNotExistProductId(id);
        Product product = new Product(id, productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        productDao.update(product);
    }
    
    @Override
    public void delete(final Long id) {
        validateNotExistProductId(id);
        productDao.delete(id);
    }
    
    private void validateNotExistProductId(final Long id) {
        if (isNotExistProductId(id)) {
            throw new IllegalArgumentException("[ERROR] 존재하지 않는 product id 입니다.");
        }
    }
    
    private boolean isNotExistProductId(final Long id) {
        return Objects.isNull(id) || findAll().stream()
                .noneMatch(productResponse -> Objects.equals(productResponse.getId(), id));
    }
}
