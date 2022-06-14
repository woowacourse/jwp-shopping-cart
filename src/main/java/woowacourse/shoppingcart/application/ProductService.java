package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.ProductDetailServiceResponse;
import woowacourse.shoppingcart.application.dto.ProductSaveServiceRequest;
import woowacourse.shoppingcart.application.dto.ProductsServiceResponse;
import woowacourse.shoppingcart.dao.PagingIndex;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotFoundProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(final ProductSaveServiceRequest productRequest) {
        return productDao.save(productRequest.toEntity());
    }

    public ProductDetailServiceResponse findProductById(final Long productId) {
        final Product product = productDao.findProductById(productId)
                .orElseThrow(NotFoundProductException::new);
        return ProductDetailServiceResponse.from(product);
    }

    public ProductsServiceResponse findProducts(final int page, final int limit) {
        final List<Product> products = productDao.findProducts(PagingIndex.from(page, limit));
        final int productCount = productDao.countProducts();
        return ProductsServiceResponse.from(productCount, products);
    }

    public void deleteProductById(final Long productId) {
        int effectedRowCount = productDao.delete(productId);
        if (effectedRowCount == 0) {
            throw new NotFoundProductException();
        }
    }
}
