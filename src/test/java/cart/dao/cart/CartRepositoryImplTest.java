package cart.dao.cart;

import cart.dao.product.ProductDao;
import cart.dao.product.ProductRepositoryImpl;
import cart.dao.user.UserDao;
import cart.dao.user.UserRepositoryImpl;
import cart.domain.TestFixture;
import cart.domain.cart.Cart;
import cart.domain.product.Product;
import cart.service.cart.CartRepository;
import cart.service.product.ProductRepository;
import cart.service.user.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@Import({CartRepositoryImpl.class, UserRepositoryImpl.class, ProductRepositoryImpl.class,
        CartDao.class, UserDao.class, ProductDao.class})
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

    @BeforeEach
    void initializeData() {
        userId = userRepository.save(TestFixture.ZUNY);
        chickenId = productRepository.save(TestFixture.CHICKEN);
        pizzaId = productRepository.save(TestFixture.PIZZA);
    }

    @AfterEach
    void deleteData() {
        userRepository.deleteById(userId);
        productRepository.deleteById(chickenId);
        productRepository.deleteById(pizzaId);
    }

    @DisplayName("Cart를 생성할 수 있다.")
    @Test
    void createCart() {
        Product chicken = productRepository.findById(chickenId);
        Cart cart = new Cart(chicken);

        cartRepository.save(cart, userId);

        List<Cart> findCart = cartRepository.findAllByUserId(userId);
        assertThat(findCart).hasSize(1);
        Product product = findCart.get(0).getProduct();
        assertThat(product.getName()).isEqualTo(TestFixture.CHICKEN.getName());
        assertThat(product.getId()).isEqualTo(chickenId);
    }

    @DisplayName("Cart를 삭제할 수 있다.")
    @Test
    void deleteCart() {
        Product chicken = productRepository.findById(chickenId);
        Cart cart = new Cart(chicken);

        Long savedCartId = cartRepository.save(cart, userId);
        assertThat(cartRepository.findAllByUserId(userId)).hasSize(1);
        cartRepository.deleteById(savedCartId);

        assertThat(cartRepository.findAllByUserId(userId)).hasSize(0);
    }
}
