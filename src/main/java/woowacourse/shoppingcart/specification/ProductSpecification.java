package woowacourse.shoppingcart.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.NotExistProductException;
import woowacourse.shoppingcart.exception.notfound.OverQuantityException;

@Component
@RequiredArgsConstructor
public class ProductSpecification {

    private final ProductDao productDao;

    public void validateForAddOrUpdate(long productId, long cartItemCount) {
        validateExistProduct(productId);
        Product findProduct = validateExistProductAndGet(productId);
        validateOverQuantity(findProduct.getQuantity(), cartItemCount);
        ;
    }

    private void validateExistProduct(long productId) {
        try {
            productDao.findProductById(productId);
        } catch (Exception e) {
            throw new NotExistProductException();
        }
    }

    private Product validateExistProductAndGet(long productId) {
        return productDao.findProductById(productId)
                .orElseThrow(NotExistProductException::new);
    }

    private void validateOverQuantity(long quantity, long cartItemCount) {
        if (quantity < cartItemCount) {
            throw new OverQuantityException();
        }
    }
}
