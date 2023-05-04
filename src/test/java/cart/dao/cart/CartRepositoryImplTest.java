package cart.dao.cart;

import cart.dao.product.ProductDao;
import cart.dao.product.ProductRepositoryImpl;
import cart.dao.user.UserDao;
import cart.dao.user.UserRepositoryImpl;
import cart.domain.TestFixture;
import cart.domain.cart.Cart;
import cart.domain.cart.CartRepository;
import cart.domain.product.Product;
import cart.domain.product.ProductRepository;
import cart.domain.user.User;
import cart.domain.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@Import({CartRepositoryImpl.class, UserRepositoryImpl.class, ProductRepositoryImpl.class,
        CartDao.class, UserDao.class, ProductDao.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class CartRepositoryImplTest {
    static Long userId, pizzaId, chickenId;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeAll
    void initializeData() {
        userId = userRepository.save(TestFixture.ZUNY);
        chickenId = productRepository.save(TestFixture.CHICKEN);
        pizzaId = productRepository.save(TestFixture.PIZZA);
    }

    @AfterAll
    void deleteData() {
        userRepository.deleteById(userId);
        productRepository.deleteById(chickenId);
        productRepository.deleteById(pizzaId);
    }

    @DisplayName("Cart를 생성할 수 있다.")
    @Test
    void createCart() {
        User zuny = userRepository.findById(userId);
        Product chicken = productRepository.findById(chickenId);
        Cart cart = new Cart(zuny, chicken);

        cartRepository.save(cart);

        List<Cart> findCart = cartRepository.findAllByUserId(userId);
        assertThat(findCart).hasSize(1);
        User user = findCart.get(0).getUser();
        assertThat(user.getEmail()).isEqualTo(TestFixture.ZUNY.getEmail());
        assertThat(user.getPassword()).isEqualTo(TestFixture.ZUNY.getPassword());
    }

    @DisplayName("Cart를 삭제할 수 있다.")
    @Test
    void deleteCart() {
        User zuny = userRepository.findById(userId);
        Product chicken = productRepository.findById(chickenId);
        Cart cart = new Cart(zuny, chicken);

        Long savedCartId = cartRepository.save(cart);
        assertThat(cartRepository.findAllByUserId(userId)).hasSize(1);
        cartRepository.deleteById(savedCartId);

        assertThat(cartRepository.findAllByUserId(userId)).hasSize(0);
    }
}
