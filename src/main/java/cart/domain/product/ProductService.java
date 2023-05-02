package cart.domain.product;

import cart.exception.ErrorCode;
import cart.exception.GlobalException;
import cart.web.controller.product.dto.ProductRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ProductService {

    public final Logger log = LoggerFactory.getLogger(getClass());

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> getProducts() {
        return productDao.findAll();
    }

    public Long save(final ProductRequest productRequest) {
        final Product product = map2Product(productRequest);
        return productDao.insert(product);
    }

    public void update(final Long id, final ProductRequest productRequest) {
        final Product product = map2Product(productRequest);
        int updatedCount = productDao.update(id, product);
        if (updatedCount != 1) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
    }

    private Product map2Product(final ProductRequest productRequest) {
        return new Product(
                productRequest.getName(),
                productRequest.getImageUrl(),
                productRequest.getPrice(),
                productRequest.getCategory());
    }

    public void delete(final Long id) {
        int deletedCount = productDao.deleteById(id);
        if (deletedCount == 0) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        if (deletedCount > 1) {
            log.error("error = {}", "삭제 상황에서 중복된 상품이 존재합니다. DB를 확인하세요.");
            throw new GlobalException(ErrorCode.INVALID_DELETE);
        }
    }

    @Transactional(readOnly = true)
    public Product getById(final Long id) {
        final Optional<Product> productById = productDao.findById(id);
        if (productById.isEmpty()) {
            throw new GlobalException(ErrorCode.PRODUCT_NOT_FOUND);
        }
        return productById.get();
    }
}
