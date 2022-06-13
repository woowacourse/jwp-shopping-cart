package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.CartItems;
import woowacourse.shoppingcart.domain.Products;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.DeleteCartItemRequests;
import woowacourse.shoppingcart.dto.FindAllCartItemResponse;
import woowacourse.shoppingcart.dto.UpdateCartItemRequests;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    public CartService(final CartItemDao cartItemDao, final ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    public void addCart(final Long customerId, final AddCartItemRequest addCartItemRequest) {
        cartItemDao.addCartItem(customerId, addCartItemRequest);
    }

    public void deleteCart(final Long customerId, final DeleteCartItemRequests deleteCartItemRequests) {
        var cartItems = deleteCartItemRequests.toCartItems();
        var cartItemIds = cartItemDao.findIdsByCustomerId(customerId);
        cartItems.checkValidIds(cartItemIds);

        for (Long cartItemId : deleteCartItemRequests.getIds()) {
            cartItemDao.deleteCartItem(cartItemId);
        }
    }

    public FindAllCartItemResponse getAllCartItem(final Long customerId) {
        var cartItems = new CartItems(cartItemDao.findByCustomerId(customerId));
        var products = getProducts(cartItems.getProductIds());
        return new FindAllCartItemResponse(cartItems, products);
    }

    private Products getProducts(List<Long> productIds) {
        var products = productIds.stream()
                .map(productDao::findProductById)
                .collect(Collectors.toList());

        return new Products(products);
    }

    public void deleteAll(Long customerId) {
        cartItemDao.deleteAllByCustomerId(customerId);
    }

    public void update(Long customerId, UpdateCartItemRequests updateCartItemRequests) {
        var cartItems = updateCartItemRequests.toCartItems();
        var cartItemIds = cartItemDao.findIdsByCustomerId(customerId);
        cartItems.checkValidIds(cartItemIds);

        for (var updateCartItemRequest : updateCartItemRequests.getCartItems()) {
            cartItemDao.update(customerId, updateCartItemRequest);
        }
    }
}
