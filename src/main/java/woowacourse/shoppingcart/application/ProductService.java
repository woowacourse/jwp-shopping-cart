package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductRepository;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.ProductResponses;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private static final int MINIMUM_PAGE = 1;
    private static final int MINIMUM_SIZE = 0;

    private final ProductRepository productDao;

    public ProductService(final ProductRepository productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    @Transactional(readOnly = true)
    public Product findProductById(final Long productId) {
        return productDao.findProductById(productId);
    }

    @Transactional(readOnly = true)
    public ProductResponses findProducts(long size, long page) {
        validateSizeAndPage(size, page);

        return new ProductResponses(productDao.findProducts(size, page));
    }

    @Transactional
    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }

    private void validateSizeAndPage(long size, long page) {
        if (size < MINIMUM_SIZE) {
            throw new IllegalArgumentException("size는 " + MINIMUM_SIZE + "이상으로 입력해야합니다.");
        }
        if (page < MINIMUM_PAGE) {
            throw new IllegalArgumentException("page는 " + MINIMUM_PAGE + "이상으로 입력해야합니다.");
        }
    }
}
