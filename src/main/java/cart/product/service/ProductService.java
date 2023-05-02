package cart.product.service;

import cart.product.dto.ProductRequest;
import cart.product.dto.ProductResponse;

import java.util.List;

public interface ProductService {
    List<ProductResponse> findAll();
    
    Long save(final ProductRequest productRequest);
    
    void update(final Long id, final ProductRequest productRequest);
    
    void delete(final Long id);
}
