package woowacourse.cartitem.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.cartitem.dao.CartItemDao;
import woowacourse.cartitem.dto.CartItemAddRequest;
import woowacourse.customer.application.CustomerService;
import woowacourse.product.dao.ProductDao;
import woowacourse.product.domain.Product;
import woowacourse.shoppingcart.exception.InvalidProductException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartItemService {

    private final CustomerService customerService;
    private final ProductDao productDao;
    private final CartItemDao cartItemDao;

    public CartItemService(
        final CustomerService customerService,
        final ProductDao productDao,
        final CartItemDao cartItemDao
    ) {
        this.customerService = customerService;
        this.productDao = productDao;
        this.cartItemDao = cartItemDao;
    }

    public Long addCartItem(final String username, final CartItemAddRequest cartItemAddRequest) {
        final Long customerId = customerService.findIdByUsername(username);
        validateProductStock(cartItemAddRequest.getProductId(), cartItemAddRequest.getQuantity());
        return cartItemDao.addCartItem(cartItemAddRequest.toCartItem(customerId));
    }

    private void validateProductStock(final Long productId, final Integer quantity) {
        final Product product = productDao.findProductById(productId)
            .orElseThrow(() -> new InvalidProductException("해당 id에 따른 상품을 찾을 수 없습니다."));
        product.checkProductAvailableForPurchase(quantity);
    }

    // public List<Cart> findCartsByCustomerName(final String customerName) {
    //     final List<Long> cartIds = findCartIdsByCustomerName(customerName);
    //
    //     final List<Cart> carts = new ArrayList<>();
    //     for (final Long cartId : cartIds) {
    //         final Long productId = cartItemDao.findProductIdById(cartId);
    //         // final Product product = productDao.findProductById(productId);
    //         // carts.add(new Cart(cartId, product));
    //     }
    //     return carts;
    // }
    //
    // private List<Long> findCartIdsByCustomerName(final String customerName) {
    //     final Long customerId = customerDao.findIdByUserName(customerName);
    //     return cartItemDao.findIdsByCustomerId(customerId);
    // }
    //
    // public void deleteCart(final String customerName, final Long cartId) {
    //     validateCustomerCart(cartId, customerName);
    //     cartItemDao.deleteCartItem(cartId);
    // }
    //
    // private void validateCustomerCart(final Long cartId, final String customerName) {
    //     final List<Long> cartIds = findCartIdsByCustomerName(customerName);
    //     if (cartIds.contains(cartId)) {
    //         return;
    //     }
    //     throw new NotInCustomerCartItemException();
    // }
}
