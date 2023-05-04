package cart.service.cart;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import cart.domain.cart.Cart;
import cart.domain.product.Product;
import cart.domain.user.User;
import cart.event.user.UserRegisteredEvent;
import cart.repository.cart.CartRepository;
import cart.repository.cart.StubCartRepository;
import cart.repository.product.ProductRepository;
import cart.repository.product.StubProductRepository;
import cart.repository.user.StubUserRepository;
import cart.repository.user.UserRepository;
import cart.service.product.ProductQueryService;
import cart.service.user.UserQueryService;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@SuppressWarnings("NonAsciiCharacters")
class CartCommandServiceTest {

    private CartRepository cartRepository;
    private UserQueryService userQueryService;
    private UserRepository userRepository;
    private CartCommandService cartCommandService;
    private ProductQueryService productQueryService;
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        cartRepository = new StubCartRepository();
        userRepository = new StubUserRepository();
        userQueryService = new UserQueryService(userRepository);
        productRepository = new StubProductRepository();
        productQueryService = new ProductQueryService(productRepository);
        cartCommandService = new CartCommandService(cartRepository, userQueryService, productQueryService);
    }

    @Test
    void 카트_추가_테스트() {
        // given
        final User savedUser = userRepository.save(new User("asdf", "1234"));
        final Cart cart = new Cart(savedUser);
        cartRepository.save(cart);

        // when
        cartCommandService.createCart(new UserRegisteredEvent(savedUser));

        // then
        final Optional<Cart> findResult = cartRepository.findByUser(savedUser);
        assertAll(
                () -> assertThat(findResult).isPresent(),
                () -> assertThat(findResult.get().getCartId().getValue()).isPositive()
        );
    }

    @Test
    void 장바구니_추가_테스트() {
        // given
        final User savedUser = userRepository.save(new User("asdf", "1234"));
        final Product savedProduct = productRepository.save(new Product("name", "image", 5));
        final Cart cart = new Cart(savedUser);
        cartRepository.save(cart);

        // when
        final Cart result = cartCommandService.addProduct(savedProduct.getProductId().getValue(),
                savedUser.getEmail().getValue());

        // then
        assertAll(
                () -> assertThat(result.getCartId().getValue()).isPositive(),
                () -> assertThat(result.getCartProducts().getCartProducts()).hasSize(1),
                () -> assertThat(
                        result.getCartProducts().getCartProducts().get(0).getCartProductId().getValue()).isPositive()
        );
    }
}
