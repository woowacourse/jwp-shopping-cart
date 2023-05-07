package cart.service;

import cart.dao.CartDao;
import cart.dao.ProductDao;
import cart.dao.entity.Product;
import cart.dto.ProductResponse;
import cart.dto.ProductSaveRequest;
import cart.dto.ProductUpdateRequest;
import org.springframework.dao.IncorrectUpdateSemanticsDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProductService {

    private static final String NO_PRODUCT_EXCEPTION_MESSAGE = "상품을 찾을 수 없습니다.";

    private final ProductDao productDao;
    private final CartDao cartDao;
    private final ProductMapper productMapper;

    public ProductService(ProductDao productDao, CartDao cartDao, ProductMapper productMapper) {
        this.productDao = productDao;
        this.cartDao = cartDao;
        this.productMapper = productMapper;
    }

    public Long save(final ProductSaveRequest productSaveRequest) {
        final Product product = productMapper.mapFrom(productSaveRequest);
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> findAll() {
        final List<Product> products = productDao.findAll();
        return products.stream()
                .map(ProductResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public void delete(final Long id) {
        productDao.delete(id);
        cartDao.deleteByProductId(id);
    }

    public void update(final Long id, final ProductUpdateRequest request) {
        final int affectedRows = productDao.update(id, productMapper.mapFrom(request));

        if (affectedRows == 0) {
            throw new IncorrectUpdateSemanticsDataAccessException("수정에 실패했습니다.");
        }
    }
}
