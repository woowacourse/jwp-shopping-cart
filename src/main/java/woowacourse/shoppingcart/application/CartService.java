package woowacourse.shoppingcart.application;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.cart.Cart;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CartUpdationRequest;
import woowacourse.shoppingcart.exception.bodyexception.DuplicatedProductInCartException;
import woowacourse.shoppingcart.exception.bodyexception.NotExistProductInCartException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductService productService;

    public CartService(final CartItemDao cartItemDao, ProductService productService) {
        this.cartItemDao = cartItemDao;
        this.productService = productService;
    }

    public void addCart(final Long productId, final Customer customer) {
        boolean isExist = cartItemDao.existProduct(customer.getId(), productId);
        if (isExist) {
            throw new DuplicatedProductInCartException();
        }

        Product product = productService.findProductById(productId);
        cartItemDao.addCartItem(customer.getId(), product.getId());
    }

    public List<Cart> getCarts(Customer customer) {
        return cartItemDao.getCartsByCustomerId(customer.getId());
    }

    public Cart updateProductInCart(Customer customer, CartUpdationRequest request, Long productId) {
        Long updatedProductId = productService.findProductById(productId).getId();
        validateExistProductInCart(customer, updatedProductId);

        cartItemDao.updateCartItem(customer.getId(), request.getQuantity(), updatedProductId);
        return cartItemDao.findCartByProductCustomer(customer.getId(), updatedProductId);
    }

    public void deleteProductInCart(Customer customer, Long productId) {
        Long deleteProductId = productService.findProductById(productId).getId();
        validateExistProductInCart(customer, deleteProductId);

        cartItemDao.deleteCartItem(customer.getId(), deleteProductId);
    }

    private void validateExistProductInCart(Customer customer, Long productId) {
        boolean isExist = cartItemDao.existProduct(customer.getId(), productId);
        if (!isExist) {
            throw new NotExistProductInCartException();
        }
    }
}
