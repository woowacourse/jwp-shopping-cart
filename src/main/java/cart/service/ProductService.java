package cart.service;

import cart.domain.Product;
import cart.dto.ProductDto;
import cart.dao.ProductDao;
import cart.dto.ProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductDao productDao;
    
    public List<ProductDto> findAll() {
        List<Product> products = productDao.findAll();
        return products.stream()
                .map(ProductDto::from)
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
