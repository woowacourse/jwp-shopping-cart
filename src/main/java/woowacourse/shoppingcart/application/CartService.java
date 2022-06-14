package woowacourse.shoppingcart.application;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.specification.CustomerSpecification;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.exception.notfound.AlreadyCartItemExistException;
import woowacourse.shoppingcart.exception.notfound.InvalidProductException;
import woowacourse.shoppingcart.exception.NotExistProductException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.notfound.OverQuantityException;
import woowacourse.shoppingcart.specification.CartSpecification;
import woowacourse.shoppingcart.specification.ProductSpecification;

@Service
@Transactional(rollbackFor = Exception.class)
@RequiredArgsConstructor
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerSpecification customerSpec;

    private final ProductSpecification productSpec;
    private final CartSpecification cartSpec;

    public Long addCartItem(long customerId, long productId, long cartItemCount) {
        customerSpec.validateCustomerExists(customerId);
        productSpec.validateForAddOrUpdate(productId, cartItemCount);
        cartSpec.validateForAdd(customerId, productId);

        try {
            return cartItemDao.addCartItem(customerId, productId, cartItemCount);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public void changeCartItemCount(long customerId, int productId, long cartItemCount) {
        customerSpec.validateCustomerExists(customerId);
        productSpec.validateForAddOrUpdate(productId, cartItemCount);

        cartItemDao.updateCartItemDao(customerId, productId, cartItemCount);
    }

    public void deleteCart(final long customerId, final Long productId) {
        customerSpec.validateCustomerExists(customerId);
        cartSpec.validateCustomerHasCart(customerId, productId);

        cartItemDao.deleteCartItemByProductId(productId);
    }
}
