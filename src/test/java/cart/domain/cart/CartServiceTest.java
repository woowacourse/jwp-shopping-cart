package cart.domain.cart;

import cart.domain.TestFixture;
import cart.domain.cart.dto.CartAdditionProductDto;
import cart.domain.cart.dto.CartDto;
import cart.domain.product.ProductRepository;
import cart.domain.user.UserRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
class CartServiceTest {
    static Long userId, pizzaId, chickenId;

    @Autowired
    private CartService cartService;

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

    @DisplayName("특정 유저의 장바구니에 담긴 상품을 가져올 수 있다.")
    @Test
    void findProductOfUser() {
        cartService.addProduct(new CartAdditionProductDto(userId, pizzaId));
        cartService.addProduct(new CartAdditionProductDto(userId, chickenId));

        List<CartDto> cartOfUser = cartService.getCartOfUser(userId);

        assertThat(cartOfUser).extractingResultOf("getName")
                .containsExactlyInAnyOrder(TestFixture.CHICKEN.getName(), TestFixture.PIZZA.getName());
    }

    @DisplayName("특정 유저의 장바구니에 상품을 삭제할 수 있다.")
    @Test
    void deleteProduct() {
        Long savedCartId = cartService.addProduct(new CartAdditionProductDto(userId, pizzaId));

        assertThat(cartService.getCartOfUser(userId)).hasSize(1);
        cartService.deleteProduct(savedCartId);

        assertThat(cartService.getCartOfUser(userId)).hasSize(0);
    }
}
