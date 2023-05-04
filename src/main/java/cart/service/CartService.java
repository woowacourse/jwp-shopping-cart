package cart.service;

import cart.domain.Cart;
import cart.domain.CartItem;
import cart.domain.CartRepository;
import cart.domain.Product;
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
                .orElseThrow(() -> new IllegalArgumentException("잘못된 경로입니다"));
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
