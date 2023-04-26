package cart.service;

import cart.domain.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import cart.entity.ProductEntity;
import cart.repository.ProductDao;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public ProductResponse create(ProductRequest productRequest) {
        Product product = Product.from(productRequest);
        ProductEntity created = productDao.save(product);
        return ProductResponse.from(created);
    }

    public List<ProductResponse> findAll() {
        List<ProductEntity> products = productDao.findAll();
        return products.stream()
                .map(ProductResponse::from)
                .collect(Collectors.toList());
    }

    public ProductResponse update(ProductRequest productRequest, Long id) {
        ProductEntity productEntity = productDao.findById(id);
        productEntity.update(productRequest);
        productDao.update(productEntity);
        return ProductResponse.from(productEntity);
    }

    public void deleteById(Long id) {
        productDao.deleteById(id);
    }
}
