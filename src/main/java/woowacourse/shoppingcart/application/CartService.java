package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CartItemsResponse;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductsRequest;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemDao cartItemDao;

    public CartService(CartItemDao cartItemDao) {
        this.cartItemDao = cartItemDao;
    }

    public CartResponse addCartItem(Long customerId, CartRequest cartRequest) {
        checkCartItemDuplication(customerId, cartRequest);
        cartItemDao.save(customerId, cartRequest.getProductId(), cartRequest.getQuantity());
        return new CartResponse(cartRequest.getProductId(), cartRequest.getQuantity());
    }

    private void checkCartItemDuplication(Long customerId, CartRequest cartRequest) {
        if (cartItemDao.existByCustomerIdAndProductId(customerId, cartRequest.getProductId())) {
            throw new InvalidCartItemException("장바구니에 이미 존재하는 상품입니다.");
        }
    }

    public CartItemsResponse findCartItems(Long customerId) {
        List<Cart> carts = cartItemDao.findByCustomerId(customerId);
        return new CartItemsResponse(createCartItemResponses(carts));
    }

    private List<CartItemResponse> createCartItemResponses(List<Cart> carts) {
        return carts.stream()
                .map(cart -> CartItemResponse.from(cart.getProduct(), cart.getQuantity()))
                .collect(Collectors.toList());
    }

    public void updateCartItemQuantity(Long customerId, CartRequest cartRequest) {
        checkExistCartItem(customerId, cartRequest.getProductId());
        cartItemDao.updateQuantityByCustomerIdAndProductId(customerId, cartRequest.getProductId(),
                cartRequest.getQuantity());
    }

    private void checkExistCartItem(Long customerId, Long productId) {
        if (!cartItemDao.existByCustomerIdAndProductId(customerId, productId)) {
            throw new NotInCustomerCartItemException("장바구니 아이템이 존재하지 않습니다.");
        }
    }

    public void deleteCartItems(Long customerId, ProductsRequest productsRequest) {
        for (Long productId : productsRequest.getProductIds()) {
            checkExistCartItem(customerId, productId);
            cartItemDao.deleteByCustomerIdAndProductId(customerId, productId);
        }
    }
}
