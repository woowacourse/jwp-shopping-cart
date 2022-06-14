package woowacourse.shoppingcart.specification;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.exception.notfound.AlreadyCartItemExistException;

@Component
@RequiredArgsConstructor
public class CartSpecification {

    private final CartItemDao cartItemDao;

    public void validateForAdd(long customerId, long productId) {
        validateOverQuantity(customerId, productId);
    }

    private void validateOverQuantity(long customerId, long productId) {
        boolean exist = cartItemDao.existCartItem(customerId, productId);
        if (exist) {
            throw new AlreadyCartItemExistException();
        }
    }

    public void validateCustomerHasCart(long customerId, long productId) {
        final List<Long> cartIds = findCartIdsByCustomerId(customerId);
        if (cartIds.contains(productId)) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }

    private List<Long> findCartIdsByCustomerId(final long customerId) {
        return cartItemDao.findProductIdsByCustomerId(customerId);
    }
}
