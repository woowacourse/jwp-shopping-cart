package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.ProductRequest;
import woowacourse.shoppingcart.dto.response.ProductResponse;
import woowacourse.shoppingcart.dto.response.ProductResponses;
import woowacourse.shoppingcart.exception.InvalidPageException;

@Service
@Transactional(readOnly = true)
public class ProductService {
    private static final int MINIMUM_NUMBER_OF_PAGE = 1;

    private final ProductDao productDao;

    public ProductService(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public Long addProduct(ProductRequest productRequest) {
        Product product = productDao.save(productRequest.toProduct());
        return product.getId();
    }

    public ProductResponses findProducts(int size, int page) {
        validateSizeOfPage(size);
        validatePage(page);
        List<Product> products = productDao.findProducts(size, size * (page - 1));
        return ProductResponses.from(products);
    }

    public ProductResponse findProductById(Long productId) {
        Product product = productDao.findProductById(productId);
        return ProductResponse.from(product);
    }

    @Transactional
    public void deleteProductById(Long productId) {
        productDao.delete(productId);
    }

    private void validateSizeOfPage(int size) {
        if (size < 0) {
            throw new InvalidPageException();
        }
    }

    private void validatePage(int page) {
        if (page < MINIMUM_NUMBER_OF_PAGE) {
            throw new InvalidPageException();
        }
    }
}
