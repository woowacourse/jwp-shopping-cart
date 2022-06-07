package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.CartItems;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.domain.Products;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.DeleteCartItemIdsRequest;
import woowacourse.shoppingcart.dto.FindAllCartItemResponse;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;
import woowacourse.shoppingcart.dto.UpdateCartItemsRequest;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

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

    public List<Cart> findCartsByCustomerName(final String customerName) {
        final List<Long> cartIds = null;

        final List<Cart> carts = new ArrayList<>();
        for (final Long cartId : cartIds) {
            final Long productId = cartItemDao.findProductIdById(cartId);
            final Product product = productDao.findProductById(productId);
            carts.add(new Cart(cartId, product));
        }
        return carts;
    }

    public void addCart(final Long customerId, final AddCartItemRequest addCartItemRequest) {
        cartItemDao.addCartItem(customerId, addCartItemRequest);
    }

    public void deleteCart(final Long customerId, final DeleteCartItemIdsRequest deleteCartItemIdsRequest) {
        var cartItemIds = deleteCartItemIdsRequest.getCartItemIds();
        validateCustomerCart(customerId, cartItemIds);

        for (Long cartItemId : cartItemIds) {
            cartItemDao.deleteCartItem(cartItemId);
        }
    }

    private void validateCustomerCart(final Long customerId, final List<Long> cartItemIds) {
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        if (!cartIds.containsAll(cartItemIds)) {
            throw new NotInCustomerCartItemException();
        }
    }

    public FindAllCartItemResponse getAllCartItem(final Long customerId) {
        var cartItems = new CartItems(cartItemDao.findByCustomerId(customerId));
        var products = findProducts(cartItems.getProductIds());
        return new FindAllCartItemResponse(cartItems, products);
    }

    private Products findProducts(List<Long> productIds) {
        var products = productIds.stream()
                .map(productDao::findProductById)
                .collect(Collectors.toList());

        return new Products(products);
    }

    public void deleteAll(Long customerId) {
        cartItemDao.deleteAllByCustomerId(customerId);
    }

    public void update(Long customerId, UpdateCartItemsRequest updateCartItemsRequest) {
        for (UpdateCartItemRequest updateCartItemRequest : updateCartItemsRequest.getProducts()) {
            cartItemDao.update(customerId, updateCartItemRequest);
        }
    }
}
