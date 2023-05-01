package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ProductService {
    private final ProductDao productDao;
    
    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        return productDao.findAll().stream()
                .map(ProductResponse::from)
                .collect(Collectors.toUnmodifiableList());
    }
    
    public Long save(final ProductRequest productRequest) {
        Product product = new Product(productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        return productDao.save(product);
    }
    
    public void update(final Long id, final ProductRequest productRequest) {
        validateNotExistProductId(id);
        Product product = new Product(id, productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        productDao.update(product);
    }
    
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
