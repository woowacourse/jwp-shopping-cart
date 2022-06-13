package woowacourse.shoppingcart.application;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.Entity.CartEntity;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.domain.Carts;
import woowacourse.shoppingcart.dto.CartIdRequest;
import woowacourse.shoppingcart.dto.CartProductInfoRequest;
import woowacourse.shoppingcart.dto.CartProductInfoResponse;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.ProductIdRequest;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;
import woowacourse.shoppingcart.repository.CartTotalRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {

    private final CartTotalRepository cartTotalRepository;

    public CartService(final CartTotalRepository cartTotalRepository) {
        this.cartTotalRepository = cartTotalRepository;
    }

    @Transactional(readOnly = true)
    public List<CartResponse> findCartsByCustomerId(final Long customerId) {
        cartTotalRepository.validateCustomerId(customerId);

        return cartTotalRepository.findCartsByCustomerId(customerId)
                .stream()
                .map(CartResponse::of)
                .collect(Collectors.toList());
    }

    @Transactional
    public List<CartProductInfoResponse> addCarts(final List<ProductIdRequest> productIdRequests,
                                                  final Long customerId) {
        cartTotalRepository.validateCustomerId(customerId);
        List<Long> productIds = productIdRequests.stream()
                .map(ProductIdRequest::getId)
                .collect(Collectors.toList());

        Carts containedCarts = new Carts(cartTotalRepository.findByCustomerIdAndProductIds(customerId, productIds));
        cartTotalRepository.plusQuantityByIds(containedCarts.getCartIds());
        cartTotalRepository.createAll(customerId, containedCarts.findNotInProductIds(productIds));

        return productIdRequests.stream()
                .map(productIdRequest -> toCartInfoRequest(productIdRequest, customerId))
                .collect(Collectors.toList());
    }

    private CartProductInfoResponse toCartInfoRequest(final ProductIdRequest productIdRequest, final Long customerId) {
        Long productId = productIdRequest.getId();

        cartTotalRepository.validateCustomerId(customerId);
        cartTotalRepository.validateProductId(productId);

        Long id = cartTotalRepository.findIdByCustomerIdAndProductId(customerId, productId);
        return new CartProductInfoResponse(id, cartTotalRepository.findById(id).getQuantity());
    }

    @Transactional
    public CartProductInfoResponse patchCart(final CartProductInfoRequest cartProductInfoRequest,
                                             final Long customerId) {
        Long productId = cartProductInfoRequest.getId();

        cartTotalRepository.validateProductId(productId);
        cartTotalRepository.validateCustomerId(customerId);

        Cart cart = cartTotalRepository.update(
                getCartEntity(cartProductInfoRequest.getId(), customerId, cartProductInfoRequest));
        return new CartProductInfoResponse(cart.getId(), cart.getQuantity());
    }

    private CartEntity getCartEntity(final Long id, final Long customerId, final CartProductInfoRequest infoRequest) {
        return new CartEntity(id, customerId, infoRequest.getId(), infoRequest.getQuantity());
    }

    @Transactional
    public void deleteCarts(final Long customerId, final List<CartIdRequest> cartIdRequests) {
        List<Long> cartIds = cartIdRequests.stream()
                .map(CartIdRequest::getId)
                .collect(Collectors.toList());

        cartTotalRepository.validateCustomerId(customerId);
        validateCustomerCarts(cartIds, customerId);

        cartTotalRepository.deleteByIds(cartIds);
    }

    private void validateCustomerCarts(List<Long> cartIds, Long customerId) {
        cartIds.forEach(cartId -> validateCustomerCart(cartId, customerId));
    }

    private void validateCustomerCart(final Long cartId, final Long customerId) {
        List<Cart> cartsByCustomerId = cartTotalRepository.findCartsByCustomerId(customerId);
        boolean noCustomerCart = cartsByCustomerId.stream()
                .map(Cart::getId)
                .noneMatch(id -> id.equals(cartId));
        if (noCustomerCart) {
            throw new NotInCustomerCartItemException();
        }
    }
}
