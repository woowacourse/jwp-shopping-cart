package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.ProductResponse;
import woowacourse.shoppingcart.application.dto.ProductSaveRequest;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;

@Service
@Transactional(rollbackFor = Exception.class)
public class ProductService {

    private final ProductDao productDao;

    public ProductService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    public Long save(ProductSaveRequest request) {
        Long id = productDao.save(request.toEntity());
        return id;
    }

    public ProductResponse findById(Long saveId) {
        Product product = productDao.findById(saveId);
        return new ProductResponse(product);
    }

//    public List<Product> findProducts() {
//        return productDao.findProducts();
//    }

    public Long addProduct(final Product product) {
        return productDao.save(product);
    }

    public void deleteProductById(final Long productId) {
        productDao.delete(productId);
    }
}
