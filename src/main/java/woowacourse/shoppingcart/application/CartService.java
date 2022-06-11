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
        Optional<Cart> findCart = cartDao
                .findByCustomerIdAndProductId(customerId, productId);
        Product findProduct = productDao.findProductById(productId)
                .orElseThrow(() -> new ProductNotFoundException(CODE_3001.getMessage()));

        if (findCart.isPresent()) {
            Cart update = cartDao.update(new Cart(findCart.get().getId(), productId, customerId,
                    cartSetRequest.getQuantity()));
            return new CartSetResponse(findProduct, update, false);
        }
        Cart save = cartDao.save(new Cart(productId, customerId, cartSetRequest.getQuantity()));
        return new CartSetResponse(findProduct, save, true);
    }

    public List<CartProduct> findCartsByCustomerEmail(final String email) {
        Long customerId = customerDao.findIdByEmail(email);
        List<Cart> carts = cartDao.findByCustomerId(customerId);

        List<CartProduct> cartProducts = new ArrayList<>();

        for (Cart cart : carts) {
            Product product = productDao.findProductById(cart.getProductId()).get();
            cartProducts.add(new CartProduct(product.getId(), product.getImage(), product.getName(), product.getPrice(),
                    cart.getQuantity()));
        }
        return cartProducts;
    }

    public void deleteCart(String email, CartDeleteRequest cartDeleteRequest) {
        Long customerId = customerDao.findIdByEmail(email);
        cartDao.deleteByCustomerIdAndCartIds(customerId, cartDeleteRequest.getProductIds());
    }
}
