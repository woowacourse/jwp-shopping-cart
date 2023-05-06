package cart.service;

import cart.domain.cart.Cart;
import cart.domain.item.Item;
import cart.domain.user.User;
import cart.exception.item.ItemNotFoundException;
import cart.exception.user.UserNotFoundException;
import cart.repository.CartRepository;
import cart.repository.ItemRepository;
import cart.repository.UserRepository;
import cart.service.dto.CartDto;
import cart.service.dto.ItemDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class CartService {

    private static final String NOT_FOUND_USER_MESSAGE = "존재하지 않는 사용자입니다.";

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public CartService(UserRepository userRepository, ItemRepository itemRepository, CartRepository cartRepository) {
        this.userRepository = userRepository;
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public CartDto findCart(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_MESSAGE));
        Cart cart = cartRepository.findCart(user);

        return CartDto.from(cart);
    }

    @Transactional
    public ItemDto addItem(String email, Long itemId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_MESSAGE));
        Item item = itemRepository.findById(itemId)
                .orElseThrow(() -> new ItemNotFoundException("등록하고자 하는 상품을 찾을 수 없습니다."));
        Cart cart = cartRepository.findCart(user);

        cart.addItem(item);
        cartRepository.save(cart);

        return ItemDto.from(item);
    }

    @Transactional
    public void deleteCartItem(String email, Long cartItemId) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(NOT_FOUND_USER_MESSAGE));
        Item item = itemRepository.findById(cartItemId)
                .orElseThrow(() -> new ItemNotFoundException("장바구니에서 일치하는 상품을 찾을 수 없습니다."));

        Cart cart = cartRepository.findCart(user);
        cart.removeItem(item);

        cartRepository.deleteCartItem(cart);
    }
}
