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

    private static final String NO_PRODUCT_EXCEPTION_MESSAGE = "상품을 찾을 수 없습니다.";

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
        final int affectedRows = productDao.delete(id);

        validateResult(affectedRows);
    }

    public void update(final Long id, final ProductRequest request) {
        final int affectedRows = productDao.update(id, productMapper.mapFrom(request));

        validateResult(affectedRows);
    }

    private static void validateResult(final int affectedRows) {
        if (affectedRows == 0) {
            throw new NoSuchElementException(NO_PRODUCT_EXCEPTION_MESSAGE);
        }
    }
}
