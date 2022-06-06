package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.global.exception.InvalidProductException;
import woowacourse.shoppingcart.application.dto.CartResponse;
import woowacourse.shoppingcart.application.dto.ProductResponse;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.Cart;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartItemDao cartItemDao;
    private final CustomerService customerService;
    private final ProductService productService;

    public CartService(CartItemDao cartItemDao, CustomerService customerService, ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.customerService = customerService;
        this.productService = productService;
    }

    @Transactional
    public void save(final Long customerId, final Long productId) {
        validateSave(customerId, productId);
        cartItemDao.save(customerId, productId, 1);
    }

    private void validateSave(Long customerId, Long productId) {
        customerService.findById(customerId);
        productService.findById(productId);
    }

    public CartResponse findById(Long id) {
        Cart cart = cartItemDao.findById(id)
                .orElseThrow(() -> new InvalidProductException("[ERROR] ID가 존재하지 않습니다."));
        ProductResponse product = productService.findById(cart.getProductId());
        return new CartResponse(cart.getId(), product.getName(), product.getPrice(), cart.getQuantity(), product.getImageUrl());
    }

    public List<CartResponse> findAll(Long customerId) {
        List<Cart> carts = cartItemDao.findAllByCustomerId(customerId);
        List<CartResponse> cartResponses = new ArrayList<>();
        for (Cart cart : carts) {
            ProductResponse product = productService.findById(cart.getProductId());
            cartResponses.add(new CartResponse(cart.getId(), product.getName(), product.getPrice(), cart.getQuantity(), product.getImageUrl()));
        }

        return cartResponses;
    }

    public void updateQuantity(Long customerId, long productId, int quantity) {
        cartItemDao.updateQuantity(customerId, productId, quantity);
    }

    public void delete(Long customerId, Long productId) {
        cartItemDao.deleteByCustomerIdAndProductId(customerId, productId);
    }
//    public List<Cart> findCartsByCustomerName(final String customerName) {
//        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
//
//        final List<Cart> carts = new ArrayList<>();
//        for (final Long cartId : cartIds) {
//            final Long productId = cartItemDao.findProductIdById(cartId);
//            final Product product = productDao.findProductById(productId);
//            carts.add(new Cart(cartId, product));
//        }
//        return carts;
//    }
//
//    private List<Long> findCartIdsByCustomerName(final String customerName) {
//        final Long customerId = customerDao.findIdByUserName(customerName);
//        return cartItemDao.findIdsByCustomerId(customerId);
//    }
//
//
//    public void deleteCart(final String customerName, final Long cartId) {
//        validateCustomerCart(cartId, customerName);
//        cartItemDao.deleteCartItem(cartId);
//    }
//
//    private void validateCustomerCart(final Long cartId, final String customerName) {
//        final List<Long> cartIds = findCartIdsByCustomerName(customerName);
//        if (cartIds.contains(cartId)) {
//            return;
//        }
//        throw new NotInCustomerCartItemException();
//    }
}
