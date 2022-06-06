package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.ProductFindResponse;
import woowacourse.shoppingcart.dto.ProductSaveRequest;
import woowacourse.shoppingcart.dto.ProductSaveResponse;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {
    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public List<Product> findProducts() {
        return productDao.findProducts();
    }

    public ProductSaveResponse addProduct(ProductSaveRequest request) {
        return new ProductSaveResponse(productDao.save(request.toEntity()));
    }

    public ProductFindResponse findProductById(final Long productId) {
        Product product = productDao.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException("상품이 존재하지 않습니다"));
        return new ProductFindResponse(product);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
