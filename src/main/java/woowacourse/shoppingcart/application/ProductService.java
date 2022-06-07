package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.response.ProductResponse;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.datanotfound.ProductDataNotFoundException;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    public ProductResponse findById(final Long productId) {
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new ProductDataNotFoundException("존재하지 않는 상품입니다."));
        return ProductResponse.from(product);
    }

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public List<ProductResponse> findProductsInPage(final Long pageNum, final Long limitCount) {
        List<Product> products = productDao.findProductsInPage(pageNum, limitCount);
        return ProductResponse.from(products);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
