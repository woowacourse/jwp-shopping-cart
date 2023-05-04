package cart.repository;

import static cart.factory.ProductFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.Cart;
import cart.domain.CartItem;
import cart.domain.Product;
import cart.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;

@JdbcTest
class CartDbRepositoryTest {

    private CartRepository cartRepository;

    private UserRepository userRepository;

    private ProductRepository productRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        cartRepository = new CartDbRepository(jdbcTemplate);
        userRepository = new UserDbRepository(jdbcTemplate);
        productRepository = new ProductDbRepository(jdbcTemplate);
    }

    @Test
    @DisplayName("유저에 해당하는 장바구니를 가져올 수 있다.")
    void find_by_user_success() {
        //given
        User user = userRepository.findByEmail("rosie@wooteco.com").get();
        //when
        Cart cart = cartRepository.findByUser(user);
        //then
        assertThat(cart).isNotNull();
        assertThat(cart.getUser()).isEqualTo(user);
    }

    @Test
    @DisplayName("장바구니에 상품을 추가할 수 있다.")
    void add_cart_item_success() {
        //given
        User user = userRepository.findByEmail("rosie@wooteco.com").get();
        Cart cart = cartRepository.findByUser(user);
        productRepository.add(createProduct());
        Product product = productRepository.findAll().get(0);
        CartItem cartItem = new CartItem(product);
        //when
        cartRepository.addCartItem(cart, cartItem);
        //then
        Cart after = cartRepository.findByUser(user);
        assertThat(after.getCartItems()).hasSize(cart.getCartItems().size() + 1);
    }

    @Test
    @DisplayName("장바구니에서 상품을 삭제할 수 있다.")
    void remove_cart_item_success() {
        //given
        User user = userRepository.findByEmail("rosie@wooteco.com").get();
        addDummyProduct(user);

        Cart cart = cartRepository.findByUser(user);
        //when
        Long cartItemId = cart.getCartItems().get(0).getId();
        cartRepository.removeCartItem(cartItemId);
        //then
        Cart after = cartRepository.findByUser(user);
        assertThat(after.getCartItems()).hasSize(cart.getCartItems().size() - 1);
    }

    private void addDummyProduct(User user) {
        Cart cart = cartRepository.findByUser(user);
        productRepository.add(createProduct());
        Product product = productRepository.findAll().get(0);
        CartItem cartItem = new CartItem(product);
        cartRepository.addCartItem(cart, cartItem);
    }
}