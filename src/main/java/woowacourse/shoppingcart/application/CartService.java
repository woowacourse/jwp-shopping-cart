package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.Entity.CartEntity;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartIdRequest;
import woowacourse.shoppingcart.dto.CartProductInfoRequest;
import woowacourse.shoppingcart.dto.CartProductInfoResponse;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductIdRequest;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.repository.CartItemRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartItemRepository cartItemRepository;

    public CartService(final CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findCartsByCustomerId(final Long customerId) {
        cartItemRepository.validateCustomerId(customerId);

        return cartItemRepository.findCartsByCustomerId(customerId)
                .stream()
                .map(CartResponse::of)
                .collect(Collectors.toList());
    }

    public List<CartProductInfoResponse> addCarts(final List<ProductIdRequest> productIdRequests,
                                                  final Long customerId) {
        cartItemRepository.validateCustomerId(customerId);

        plusQuantityCarts(productIdRequests, customerId);
        createCarts(productIdRequests, customerId);
        return productIdRequests.stream()
                .map(productIdRequest -> toCartInfoRequest(productIdRequest, customerId))
                .collect(Collectors.toList());
    }

    private void plusQuantityCarts(List<ProductIdRequest> productIdRequests, Long customerId) {
        List<Long> containedCartIds = productIdRequests.stream()
                .map(ProductIdRequest::getId)
                .filter(productId -> cartItemRepository.contains(productId, customerId))
                .map(productId -> cartItemRepository.findIdByCustomerIdAndProductId(customerId, productId))
                .collect(Collectors.toList());
        cartItemRepository.plusQuantityByIds(containedCartIds);
    }

    private void createCarts(List<ProductIdRequest> productIdRequests, Long customerId) {
        List<Long> notContainedProductIds = productIdRequests.stream()
                .map(ProductIdRequest::getId)
                .filter(productId -> !cartItemRepository.contains(productId, customerId))
                .collect(Collectors.toList());
        cartItemRepository.createAll(customerId, notContainedProductIds);
    }

    private CartProductInfoResponse toCartInfoRequest(final ProductIdRequest productIdRequest, final Long customerId) {
        Long productId = productIdRequest.getId();

        cartItemRepository.validateCustomerId(customerId);
        cartItemRepository.validateProductId(productId);

        Long id = cartItemRepository.findIdByCustomerIdAndProductId(customerId, productId);
        return new CartProductInfoResponse(id, cartItemRepository.findById(id).getQuantity());
    }

    public CartProductInfoResponse patchCart(final CartProductInfoRequest cartProductInfoRequest,
                                             final Long customerId) {
        Long productId = cartProductInfoRequest.getId();

        cartItemRepository.validateProductId(productId);
        cartItemRepository.validateCustomerId(customerId);

        Cart cart = cartItemRepository.update(
                getCartEntity(cartProductInfoRequest.getId(), customerId, cartProductInfoRequest));
        return new CartProductInfoResponse(cart.getId(), cart.getQuantity());
    }

    private CartEntity getCartEntity(final Long id, final Long customerId, final CartProductInfoRequest infoRequest) {
        return new CartEntity(id, customerId, infoRequest.getId(), infoRequest.getQuantity());
    }

    public void deleteCarts(final Long customerId, final List<CartIdRequest> cartIdRequests) {
        List<Long> cartIds = cartIdRequests.stream()
                .map(CartIdRequest::getId)
                .collect(Collectors.toList());

        cartItemRepository.validateCustomerId(customerId);
        validateCustomerCarts(cartIds, customerId);

        cartItemRepository.deleteByIds(cartIds);
    }

    private void validateCustomerCarts(List<Long> cartIds, Long customerId) {
        cartIds.forEach(cartId -> validateCustomerCart(cartId, customerId));
    }

    private void validateCustomerCart(final Long cartId, final Long customerId) {
        List<Cart> cartsByCustomerId = cartItemRepository.findCartsByCustomerId(customerId);
        boolean noCustomerCart = cartsByCustomerId.stream()
                .map(Cart::getId)
                .noneMatch(id -> id.equals(cartId));
        if (noCustomerCart) {
            throw new NotInCustomerCartItemException();
        }
    }
}
