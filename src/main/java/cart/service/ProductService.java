package cart.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import cart.dao.entity.Product;
import cart.dao.product.ProductDao;
import cart.dto.product.ProductCreateRequest;
import cart.dto.product.ProductMapper;
import cart.dto.product.ProductResponse;
import cart.dto.product.ProductUpdateRequest;

@Service
public class ProductService {
    private final ProductMapper productMapper;
    private final ProductDao productDao;

    public ProductService(ProductMapper productMapper, ProductDao productDao) {
        this.productMapper = productMapper;
        this.productDao = productDao;
    }

    public Long save(ProductCreateRequest productCreateRequest) {
        final Product product = productMapper.toProduct(productCreateRequest);
        return productDao.save(product);
    }

    public List<ProductResponse> findAll() {
        final List<Product> products = productDao.findAll();
        return products.stream()
                .map(productMapper::toResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    public void delete(final Long id) {
        productDao.delete(id);
    }

    public void update(final Long id, final ProductUpdateRequest productUpdateRequest) {
        productDao.update(id, productMapper.toProduct(productUpdateRequest));
    }
}
