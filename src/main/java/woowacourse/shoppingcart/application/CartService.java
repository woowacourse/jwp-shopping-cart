package woowacourse.shoppingcart.application;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartIdRequest;
import woowacourse.shoppingcart.dto.CartProductInfoResponse;
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

    public List<Cart> findCartsByCustomerId(final Long customerId) {
        return cartItemRepository.findCartsByCustomerId(customerId);
    }

    public List<CartProductInfoResponse> addCart(final List<ProductIdRequest> productIdRequests,
                                                 final Long customerId) {
        List<Long> cartIds = new ArrayList<>();
        List<Long> productIds = productIdRequests.stream()
                .map(ProductIdRequest::getId)
                .collect(Collectors.toList());

        for (Long id : productIds) {
            cartIds.add(cartItemRepository.create(customerId, id));
        }

        return cartIds.stream()
                .map(cartItemRepository::findById)
                .map(cart -> new CartProductInfoResponse(cart.getId(), cart.getQuantity()))
                .collect(Collectors.toList());
    }

    public void deleteCarts(final Long customerId, final List<CartIdRequest> cartIdRequests) {
        List<Long> cartIds = cartIdRequests.stream()
                .map(CartIdRequest::getId)
                .collect(Collectors.toList());
        validateCustomerCarts(cartIds, customerId);
        cartItemRepository.deleteByIds(cartIds);
    }

    private void validateCustomerCarts(List<Long> cartIds, Long customerId) {
        cartIds.forEach(cartId -> validateCustomerCart(cartId, customerId));
    }

    private void validateCustomerCart(final Long cartId, final Long customerId) {
        if (cartItemRepository.findCartsByCustomerId(customerId).stream()
                .map(Cart::getId)
                .collect(Collectors.toList())
                .contains(cartId)
        ) {
            return;
        }
        throw new NotInCustomerCartItemException();
    }
}
