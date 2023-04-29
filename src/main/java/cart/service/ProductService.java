package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cart.dao.ProductDao;
import cart.dao.entity.Product;
import cart.dto.ProductCreateRequest;
import cart.dto.ProductMapper;
import cart.dto.ProductResponse;
import cart.dto.ProductUpdateRequest;

@Service
public class ProductService {
    private final static ProductMapper MAPPER = new ProductMapper();
    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(ProductCreateRequest productCreateRequest) {
        final Product product = MAPPER.toProduct(productCreateRequest);
        return productDao.save(product);
    }

    public List<ProductResponse> findAll() {
        final List<Product> products = productDao.findAll();
        return products.stream()
                .map(MAPPER::toResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    public void delete(final Long id) {
        productDao.delete(id);
    }

    public void update(final Long id, final ProductUpdateRequest productUpdateRequest) {
        productDao.update(id, MAPPER.toProduct(productUpdateRequest));
    }
}
