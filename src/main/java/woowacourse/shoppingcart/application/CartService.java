package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Username;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CartResponses;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    public Long addCart(String username, CartRequest cartRequest) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        try {
            return cartItemDao.addCartItem(customerId, cartRequest.getProductId(),
                    cartRequest.getQuantity(), cartRequest.getChecked());
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public CartResponses findCartsByUsername(String username) {
        List<Cart> carts = findCartIdsByUsername(username);
        List<CartResponse> cartResponses = carts.stream()
                .map(CartResponse::from)
                .collect(Collectors.toList());
        return new CartResponses(cartResponses);
    }

    private List<Cart> findCartIdsByUsername(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        return cartItemDao.findIByCustomerId(customerId);
    }

    public void deleteAllCart(String username) {
        Long customerId = customerDao.findByUsername(new Username(username)).getId();
        cartItemDao.deleteAllCartItem(customerId);
    }
}
