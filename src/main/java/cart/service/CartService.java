package cart.service;

import cart.domain.cart.Cart;
import cart.domain.cart.CartItem;
import cart.domain.cart.CartRepository;
import cart.domain.cart.Product;
import cart.dto.response.CartItemResponse;
import cart.persistence.dao.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    public static final String PRODUCT_ID_ERROR_MESSAGE = "상품의 id가 올바르지 않습니다.";

    private final CartRepository cartRepository;
    private final ProductDao productDao;

    public CartService(CartRepository cartRepository, ProductDao productDao) {
        this.cartRepository = cartRepository;
        this.productDao = productDao;
    }

    public List<CartItemResponse> findAllCartItems(Long memberId) {
        Cart cart = cartRepository.getCartByMemberId(memberId);
        List<CartItem> cartItems = cart.getCartItems();

        AtomicInteger index = new AtomicInteger(1);
        return cartItems.stream()
                .map(item -> new CartItemResponse(item, index.getAndIncrement()))
                .collect(Collectors.toList());
    }

    public void addCartItem(Long productId, Long memberId) {
        Cart cart = cartRepository.getCartByMemberId(memberId);
        Product product = productDao.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException(PRODUCT_ID_ERROR_MESSAGE));
        CartItem cartItem = new CartItem(product);
        cart.addItem(cartItem);
        cartRepository.save(cart);
    }

    public void deleteCartItem(int itemId, Long memberId) {
        Cart cart = cartRepository.getCartByMemberId(memberId);
        cart.removeItem(itemId);
        cartRepository.save(cart);
    }
}
