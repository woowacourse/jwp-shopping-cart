package woowacourse.shoppingcart.application;

import static woowacourse.shoppingcart.exception.ExceptionMessage.CODE_3001;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.product.Product;
import woowacourse.shoppingcart.dto.cart.CartDeleteRequest;
import woowacourse.shoppingcart.dto.cart.CartProduct;
import woowacourse.shoppingcart.dto.cart.CartSetRequest;
import woowacourse.shoppingcart.dto.cart.CartSetResponse;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

@Service
@Transactional(readOnly = true)
public class CartService {

    private final CartDao cartDao;
    private final CustomerDao customerDao;
    private final ProductDao productDao;

    public CartService(final CartDao cartItemDao, final CustomerDao customerDao, final ProductDao productDao) {
        this.cartDao = cartItemDao;
        this.customerDao = customerDao;
        this.productDao = productDao;
    }

    @Transactional
    public CartSetResponse addCart(CartSetRequest cartSetRequest, String email, Long productId) {
        Long customerId = customerDao.findIdByEmail(email);
        Optional<Cart> fountCart = cartDao
                .findByCustomerIdAndProductId(customerId, productId);
        Product fountProduct = productDao.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(CODE_3001.getMessage()));

        if (fountCart.isPresent()) {
            Cart updatedCart = cartDao.update(new Cart(fountCart.get(), cartSetRequest));
            return new CartSetResponse(fountProduct, updatedCart, false);
        }

        Cart savedCart = cartDao.save(new Cart(productId, customerId, cartSetRequest));
        return new CartSetResponse(fountProduct, savedCart, true);
    }

    public List<CartProduct> findCartsByCustomerEmail(String email) {
        Long customerId = customerDao.findIdByEmail(email);
        List<Cart> carts = cartDao.findByCustomerId(customerId);

        List<CartProduct> cartProducts = new ArrayList<>();
        for (Cart cart : carts) {
            Product product = productDao.findProductById(cart.getProductId()).get();
            cartProducts.add(new CartProduct(product, cart));
        }
        return cartProducts;
    }

    @Transactional
    public void deleteCart(String email, CartDeleteRequest cartDeleteRequest) {
        Long customerId = customerDao.findIdByEmail(email);
        cartDao.deleteByCustomerIdAndCartIds(customerId, cartDeleteRequest.getProductIds());
    }
}
