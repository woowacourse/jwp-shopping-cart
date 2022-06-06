package woowacourse.shoppingcart.application;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;

@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartItem addItem(Long customerId, Long productId, int quantity) {
        Product product = productDao.findById(productId);
        CartItem cartItem = new CartItem(product, quantity);
        return cartItemDao.save(customerId, cartItem);
    }

    @Transactional(readOnly = true)
    public List<CartItem> findItemsByCustomer(Long customerId) {
        return cartItemDao.findByCustomerId(customerId);
    }
}
