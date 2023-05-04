package cart.dao.cart;

import static org.assertj.core.api.Assertions.assertThat;

import cart.dao.product.ProductDao;
import cart.dao.product.ProductEntity;
import cart.dao.user.CartUserDao;
import cart.dao.user.CartUserEntity;
import cart.dao.user.userproduct.CartUserProductDao;
import cart.domain.cart.Cart;
import cart.domain.cart.CartRepository;
import cart.domain.product.Product;
import cart.domain.product.TestFixture;
import cart.domain.user.CartUser;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

@Import({CartRepositoryImpl.class, ProductDao.class, CartUserDao.class, CartUserProductDao.class})
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class CartRepositoryImplTest {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartUserDao cartUserDao;

    @Autowired
    private ProductDao productDao;

    @DisplayName("사용자 장바구니에 물건 추가 테스트")
    @Test
    void addProductInCart() {
        CartUser cartUser = TestFixture.CART_USER_A;
        cartUserDao.insert(new CartUserEntity(cartUser.getUserEmail(), cartUser.getPassword()));
        Long pizzaProductId = productDao.insert(new ProductEntity(TestFixture.PIZZA));
        Product product = productDao.findById(pizzaProductId).toProduct();
        Cart cart = new Cart(cartUser, Collections.emptyList());

        cartRepository.addProductInCart(cart, product);

        List<Cart> allCarts = cartRepository.findAll();
        assertThat(allCarts).hasSize(1);
        Cart findCart = allCarts.get(0);
        assertThat(findCart.getProducts()).hasSize(1);
    }

    @DisplayName("사용자 장바구니 물건 조회 테스트")
    @Test
    void findCartByCartUser() {
        CartUser cartUser = TestFixture.CART_USER_A;
        cartUserDao.insert(new CartUserEntity(cartUser.getUserEmail(), cartUser.getPassword()));
        Long pizzaProductId = productDao.insert(new ProductEntity(TestFixture.PIZZA));
        Product product = productDao.findById(pizzaProductId).toProduct();
        Cart cart = new Cart(cartUser, Collections.emptyList());
        cartRepository.addProductInCart(cart, product);

        Cart cartByCartUser = cartRepository.findCartByCartUser(cartUser);

        List<Product> productsInCart = cartByCartUser.getProducts();
        assertThat(productsInCart).hasSize(1);
        assertThat(productsInCart.get(0).getName()).isEqualTo("Pizza");
    }
}
