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
import woowacourse.shoppingcart.dto.DeleteCartItemIdsRequest;
import woowacourse.shoppingcart.dto.FindAllCartItemResponse;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;
import woowacourse.shoppingcart.dto.UpdateCartItemRequests;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

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

    public void deleteCart(final Long customerId, final DeleteCartItemIdsRequest deleteCartItemIdsRequest) {
        var cartItemIds = deleteCartItemIdsRequest.getCartItemIds().stream()
                .map(it -> it.getId())
                .collect(Collectors.toList());

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

    public void update(Long customerId, UpdateCartItemRequests updateCartItemRequests) {
        var cartIds = updateCartItemRequests.getCartItems().stream()
                .map(UpdateCartItemRequest::getId)
                .collect(Collectors.toList());

        validateCustomerCart(customerId, cartIds);

        for (UpdateCartItemRequest updateCartItemRequest : updateCartItemRequests.getCartItems()) {
            cartItemDao.update(customerId, updateCartItemRequest);
        }
    }
}
