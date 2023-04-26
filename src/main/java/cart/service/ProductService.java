package cart.service;

import cart.domain.Product;
import cart.dto.ProductDto;
import cart.dao.ProductDao;
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
    
    public void save(final Product product) {
    
    }
}
