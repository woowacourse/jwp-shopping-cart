package cart.service;

import cart.domain.Cart;
import cart.domain.CartItem;
import cart.domain.CartRepository;
import cart.domain.Product;
import cart.dto.response.CartItemResponse;
import cart.persistence.ProductDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
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

        return cartItems.stream()
                .map(CartItemResponse::new)
                .collect(Collectors.toList());
    }

    public void addCartItem(Long productId, Long memberId) {
        Cart cart = cartRepository.getCartByMemberId(memberId);
        Product product = productDao.findById(productId);
        CartItem cartItem = new CartItem(product);
        cart.addItem(cartItem);
        cartRepository.save(cart);
    }
}
