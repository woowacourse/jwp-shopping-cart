package cart.dao.cart;

import static org.assertj.core.api.Assertions.assertThat;

import cart.cart.domain.Cart;
import cart.cart.domain.CartRepository;
import cart.cart.persistence.CartRepositoryImpl;
import cart.cart.persistence.CartUserProductDao;
import cart.domain.product.TestFixture;
import cart.product.domain.Product;
import cart.product.persistence.ProductDao;
import cart.product.persistence.ProductEntity;
import cart.user.domain.CartUser;
import cart.user.persistence.CartUserDao;
import cart.user.persistence.CartUserEntity;
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
        //given
        final CartUser cartUser = TestFixture.CART_USER_A;
        cartUserDao.insert(new CartUserEntity(cartUser.getUserEmail(), cartUser.getPassword()));

        final Long pizzaProductId = productDao.insert(new ProductEntity(TestFixture.PIZZA));
        final Product product = productDao.findById(pizzaProductId).toProduct();

        //when
        cartRepository.addProductInCart(cartUser, product);

        //then
        final List<Cart> allCarts = cartRepository.findAll();
        assertThat(allCarts).hasSize(1);
        final Cart findCart = allCarts.get(0);
        assertThat(findCart.getProducts()).hasSize(1);
    }

    @DisplayName("사용자 장바구니 물건 조회 테스트")
    @Test
    void findCartByCartUser() {
        //given
        final CartUser cartUser = TestFixture.CART_USER_A;
        cartUserDao.insert(new CartUserEntity(cartUser.getUserEmail(), cartUser.getPassword()));

        final Long pizzaProductId = productDao.insert(new ProductEntity(TestFixture.PIZZA));
        final Product product = productDao.findById(pizzaProductId).toProduct();
        cartRepository.addProductInCart(cartUser, product);

        //when
        final Cart cartByCartUser = cartRepository.findCartByCartUser(cartUser);

        //then
        final List<Product> productsInCart = cartByCartUser.getProducts();
        assertThat(productsInCart).hasSize(1);
        assertThat(productsInCart.get(0).getName()).isEqualTo("Pizza");
    }

    @DisplayName("사용자 장바구니 물건 삭제 테스트")
    @Test
    void deleteProductInCart() {
        //given
        final CartUser cartUser = TestFixture.CART_USER_A;
        final CartUserEntity cartUserEntity =
                new CartUserEntity(TestFixture.CART_USER_A.getUserEmail(), TestFixture.CART_USER_A.getPassword());
        cartUserDao.insert(cartUserEntity);

        final Long pizzaProductId = productDao.insert(new ProductEntity(TestFixture.PIZZA));
        final Product product = productDao.findById(pizzaProductId).toProduct();

        cartRepository.addProductInCart(cartUser, product);
        assertThat(cartRepository.findCartByCartUser(cartUser).getProducts()).hasSize(1);

        //when
        cartRepository.deleteProductInCart(cartUser, product);

        //then
        assertThat(cartRepository.findCartByCartUser(cartUser).getProducts()).hasSize(0);
    }
}
