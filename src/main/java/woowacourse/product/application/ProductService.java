package woowacourse.product.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.product.dao.ProductDao;
import woowacourse.product.domain.Product;
import woowacourse.product.dto.ProductRequest;
import woowacourse.product.dto.ProductResponse;
import woowacourse.product.dto.ProductsResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Transactional(rollbackFor = Exception.class)
@Service
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long addProduct(final ProductRequest productRequest) {
        return productDao.save(productRequest.toProduct());
    }

    public ProductsResponse findProducts() {
        return ProductsResponse.from(productDao.findProducts());
    }

    public ProductResponse findProductById(final Long id) {
        final Product product = productDao.findProductById(id)
            .orElseThrow(() -> new InvalidProductException("해당 id에 따른 상품을 찾을 수 없습니다."));

        return ProductResponse.from(product);
    }

    public void deleteProductById(final Long id) {
        productDao.delete(id);
    }
}
