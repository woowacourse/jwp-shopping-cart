package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.CartItemIdRequest;
import woowacourse.shoppingcart.dto.CartItemQuantityRequest;
import woowacourse.shoppingcart.dto.CartItemQuantityResponse;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.ProductIdRequest;
import woowacourse.shoppingcart.repository.CartItemRepository;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartItemService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartItemService(final CartItemRepository cartItemRepository,
                           final ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public List<CartItemResponse> findCartItemsByCustomerId(final TokenRequest request) {
        List<CartItem> cartItems = cartItemRepository.findCartItemsByCustomerId(request.getId());
        return cartItems.stream()
                .map(it -> CartItemResponse.of(it, productRepository.findById(it.getProductId())))
                .collect(Collectors.toList());
    }

    public List<CartItemQuantityResponse> addCartItems(final TokenRequest tokenRequest,
                                                       final List<ProductIdRequest> productIdRequests) {
        List<Long> productIds = productIdRequests.stream().map(ProductIdRequest::getId).collect(Collectors.toList());
        List<CartItem> cartItems = cartItemRepository.addCartItems(tokenRequest.getId(), productIds);
        return cartItems.stream().map(CartItemQuantityResponse::of).collect(Collectors.toList());
    }

    public CartItemQuantityResponse updateCartItem(final TokenRequest tokenRequest,
                                                   final CartItemQuantityRequest cartItemQuantityRequest) {
        CartItem cartItem = CartItem.ofNullProductId(
                cartItemQuantityRequest.getId(),
                tokenRequest.getId(),
                cartItemQuantityRequest.getQuantity()
        );
        return CartItemQuantityResponse.of(cartItemRepository.updateCartItem(cartItem));
    }

    public void delete(final TokenRequest tokenRequest, final List<CartItemIdRequest> cartItemIdRequests) {
    }
}
