package cart.service.product;

import cart.dao.ProductDao;
import cart.domain.product.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductDeleteService {

    private final ProductDao productDao;

    public ProductDeleteService(final ProductDao productDao) {
        this.productDao = productDao;
    }

    @Transactional
    public void deleteProduct(final long id) {
        final ProductId productId = new ProductId(id);

        validateExistData(productId);

        productDao.delete(productId);
    }

    private void validateExistData(final ProductId productId) {
        if (!productDao.isExist(productId)) {
            throw new IllegalArgumentException("존재하지 않는 id 입니다.");
        }
    }
}
