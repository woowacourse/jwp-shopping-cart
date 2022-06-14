package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.request.ProductRequest;
import woowacourse.shoppingcart.application.dto.response.ProductResponse;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.datanotfound.ProductDataNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private static final int MINIMUM_PAGE_NUM = 0;
    private static final int MINIMUM_LIMIT_COUNT = 0;

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Long addProduct(final ProductRequest productRequest) {
        Product product = productRequest.toEntity();
        return productDao.save(product);
    }

    public ProductResponse findById(final Long productId) {
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new ProductDataNotFoundException("존재하지 않는 상품입니다."));
        return ProductResponse.from(product);
    }

    public List<ProductResponse> findProductsInPage(final Long pageNum, final Long limitCount) {
        validatePage(pageNum, limitCount);
        List<Product> products = productDao.findProductsInPage(pageNum, limitCount);
        return ProductResponse.from(products);
    }

    private void validatePage(final Long pageNum, final Long limitCount) {
        if (pageNum <= MINIMUM_PAGE_NUM) {
            throw new ProductDataNotFoundException("페이지는 1 이상이어야 합니다.");
        }
        if (limitCount <= MINIMUM_LIMIT_COUNT) {
            throw new ProductDataNotFoundException("상품을 조회할 개수는 1 이상이어야 합니다.");
        }
    }
}
