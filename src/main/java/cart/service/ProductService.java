package cart.service;

import cart.dao.ProductDao;
import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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
    
    public void save(final ProductRequest productRequest) {
        Product product = new Product(null, productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        productDao.save(product);
    }
    
    public void update(final Long id, final ProductRequest productRequest) {
        Product product = new Product(id, productRequest.getName(), productRequest.getImageUrl(), productRequest.getPrice());
        productDao.update(product);
    }
    
    public void delete(final Long id) {
        productDao.delete(id);
    }
}
