package cart.service;

import cart.dao.ProductDao;
import cart.dao.entity.Product;
import cart.dto.ProductRequest;
import cart.dto.ProductResponse;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    private static final String DOES_NOT_FIND_PRODUCT = "상품을 찾을 수 없습니다.";

    private final ProductDao productDao;
    private final ProductMapper productMapper;

    public ProductService(final ProductDao productDao, final ProductMapper productMapper) {
        this.productDao = productDao;
        this.productMapper = productMapper;
    }

    public Long save(final ProductRequest productRequest) {
        final Product product = productMapper.mapFrom(productRequest);
        return productDao.save(product);
    }

    public List<ProductResponse> findAll() {
        final List<Product> products = productDao.findAll();
        return products.stream()
                .map(productMapper::mapToProductResponse)
                .collect(Collectors.toUnmodifiableList());
    }

    public void delete(final Long id) {
        validateProduct(id);

        productDao.delete(id);
    }

    public void update(final Long id, final ProductRequest request) {
        validateProduct(id);

        productDao.update(id, productMapper.mapFrom(request));
    }

    private void validateProduct(final Long id) {
        if (doesNotExist(id)) {
            throw new NoSuchElementException(DOES_NOT_FIND_PRODUCT);
        }
    }

    private boolean doesNotExist(final Long id) {
        return !productDao.existBy(id);
    }
}
