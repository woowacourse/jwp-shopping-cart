package woowacourse.shoppingcart.service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.domain.Product;
import woowacourse.shoppingcart.dto.request.CartItemRequest;
import woowacourse.shoppingcart.dto.response.CartItemResponse;
import woowacourse.shoppingcart.dto.response.ProductExistingInCartResponse;
import woowacourse.shoppingcart.exception.InvalidProductException;
import woowacourse.shoppingcart.exception.notfound.CartItemNotFoundException;
import woowacourse.shoppingcart.repository.CartItemRepository;
import woowacourse.shoppingcart.repository.ProductRepository;

@Service
@Transactional(rollbackFor = Exception.class)
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    public CartService(CartItemRepository cartItemRepository, ProductRepository productRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
    }

    public Long addCart(CartItemRequest cartItemRequest, Long customerId) {
        Long productId = cartItemRequest.getProductId();
        validateAlreadyExistingProduct(customerId, productId);

        Product product = productRepository.findById(cartItemRequest.getProductId());
        CartItem cartItem = new CartItem(customerId, product, cartItemRequest.getQuantity());

        try {
            return cartItemRepository.save(customerId, cartItem);
        } catch (Exception e) {
            throw new InvalidProductException();
        }
    }

    public List<CartItemResponse> findCartItemsByCustomerId(final Long customerId) {
        List<CartItem> cartItems = cartItemRepository.findAllByCustomerId(customerId);

        return cartItems.stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toList());
    }


    private void validateAlreadyExistingProduct(Long customerId, Long productId) {
        if (cartItemRepository.isProductExisting(customerId, productId)) {
            throw new IllegalArgumentException("이미 장바구니에 담겨있는 제품입니다.");
        }
    }

    public void deleteCart(final Long customerId, final Long cartItemId) {
        validateCustomerCart(cartItemId, customerId);
        cartItemRepository.delete(cartItemId);
    }

    public void updateCartItem(Long customerId, Long cartItemId, CartItemRequest cartItemRequest) {
        validateCustomerCart(cartItemId, customerId);
        validateProductId(cartItemId, cartItemRequest.getProductId());

        Product newProduct = productRepository.findById(cartItemRequest.getProductId());
        Integer newQuantity = cartItemRequest.getQuantity();
        CartItem newCartItem = new CartItem(cartItemId, newProduct, newQuantity);

        cartItemRepository.update(cartItemId, newCartItem);
    }

    private void validateCustomerCart(final Long cartItemId, final Long customerId) {
        List<CartItem> cartItems = cartItemRepository.findAllByCustomerId(customerId);
        boolean noneMatch = cartItems.stream()
                .noneMatch(cartItem -> Objects.equals(cartItem.getId(), cartItemId));

        if (noneMatch) {
            throw new CartItemNotFoundException();
        }
    }

    private void validateProductId(final Long cartItemId, final Long productId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId);

        if (!cartItem.getProduct().getId().equals(productId)) {
            throw new IllegalArgumentException("productId는 변경될 수 없습니다.");
        }
    }

    public ProductExistingInCartResponse isProductExisting(Long customerId, Long productId) {
        boolean isExisting = cartItemRepository.isProductExisting(customerId, productId);
        return new ProductExistingInCartResponse(isExisting);
    }
}
