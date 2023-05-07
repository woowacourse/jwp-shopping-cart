package cart.repository;

import static cart.factory.ProductFactory.createProduct;
import static org.assertj.core.api.Assertions.assertThat;

import cart.domain.cart.Cart;
import cart.domain.cart.CartProduct;
import cart.domain.product.Product;
import cart.domain.user.User;
import cart.repository.cart.CartDbRepository;
import cart.repository.cart.CartRepository;
import cart.repository.product.ProductDbRepository;
import cart.repository.product.ProductRepository;
import cart.repository.user.UserDbRepository;
import cart.repository.user.UserRepository;
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
        Cart cart = cartRepository.findByNo(user.getCartNo());
        //then
        assertThat(cart).isNotNull();
        assertThat(cart.getCartNo()).isEqualTo(user.getCartNo());
    }

    @Test
    @DisplayName("장바구니에 상품을 추가할 수 있다.")
    void add_cart_item_success() {
        //given
        User user = userRepository.findByEmail("rosie@wooteco.com").get();
        Cart cart = cartRepository.findByNo(user.getCartNo());
        productRepository.add(createProduct());
        Product product = productRepository.findAll().get(0);
        CartProduct cartProduct = new CartProduct(product);
        //when
        cartRepository.addCartItem(cart, cartProduct);
        //then
        Cart after = cartRepository.findByNo(user.getCartNo());
        assertThat(after.getCartProducts()).hasSize(cart.getCartProducts().size() + 1);
    }

    @Test
    @DisplayName("장바구니에서 상품을 삭제할 수 있다.")
    void remove_cart_item_success() {
        //given
        User user = userRepository.findByEmail("rosie@wooteco.com").get();
        addDummyProduct(user);

        Cart cart = cartRepository.findByNo(user.getCartNo());
        //when
        Long cartItemId = cart.getCartProducts().get(0).getId();
        cartRepository.removeCartItem(cartItemId);
        //then
        Cart after = cartRepository.findByNo(user.getCartNo());
        assertThat(after.getCartProducts()).hasSize(cart.getCartProducts().size() - 1);
    }

    private void addDummyProduct(User user) {
        Cart cart = cartRepository.findByNo(user.getCartNo());
        productRepository.add(createProduct());
        Product product = productRepository.findAll().get(0);
        CartProduct cartProduct = new CartProduct(product);
        cartRepository.addCartItem(cart, cartProduct);
    }
}