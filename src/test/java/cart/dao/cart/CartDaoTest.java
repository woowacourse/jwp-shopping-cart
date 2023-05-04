package cart.dao.cart;

import cart.dao.product.ProductDao;
import cart.dao.product.ProductEntity;
import cart.dao.user.UserDao;
import cart.dao.user.UserEntity;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static cart.dao.cart.CartDaoTest.Data.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@Import({CartDao.class, UserDao.class, ProductDao.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@JdbcTest
class CartDaoTest {
    static Long userId;
    static Long chickenId, pizzaId;

    @Autowired
    private CartDao cartDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ProductDao productDao;


    @BeforeAll
    void initializeData() {
        userId = userDao.insert(userEntity);
        chickenId = productDao.insert(chickenEntity);
        pizzaId = productDao.insert(pizzaEntity);
    }

    @AfterAll
    void deleteData() {
        userDao.deleteById(userId);
        productDao.deleteById(chickenId);
        productDao.deleteById(pizzaId);
    }

    @DisplayName("장바구니에 상품을 추가할 수 있다.")
    @Test
    void insertCart() {
        CartEntity cartEntity = new CartEntity(null, chickenId, userId);

        cartDao.insert(cartEntity);

        List<CartEntity> allCartEntities = cartDao.findAll();
        assertThat(allCartEntities).hasSize(1);
        assertThat(allCartEntities).extractingResultOf("getProductId")
                .containsExactlyInAnyOrder(chickenId);
    }

    @DisplayName("user_id에 해당하는 카트 목록을 조회할 수 있다.")
    @Test
    void findAllByUserId() {
        CartEntity cartEntity1 = new CartEntity(null, pizzaId, userId);
        CartEntity cartEntity2 = new CartEntity(null, chickenId, userId);

        cartDao.insert(cartEntity1);
        cartDao.insert(cartEntity2);

        List<CartEntity> cartOfUser = cartDao.findByUserId(userId);
        assertThat(cartOfUser).hasSize(2);
        assertThat(cartOfUser).extractingResultOf("getProductId")
                .containsExactlyInAnyOrder(pizzaId, chickenId);
    }

    @DisplayName("카트 목록에서 특정 항목을 제거할 수 있다.")
    @Test
    void deleteById() {
        CartEntity cartEntity = new CartEntity(null, pizzaId, userId);
        Long savedCartId = cartDao.insert(cartEntity);

        assertThat(cartDao.findAll()).hasSize(1);
        cartDao.deleteById(savedCartId);

        assertThat(cartDao.findAll()).hasSize(0);
    }

    static class Data {
        static UserEntity userEntity = new UserEntity(null, "test@naver.com", "test");
        static ProductEntity chickenEntity = new ProductEntity(
                null, "Chicken", 10000, "FOOD", "chiken@test"
        );
        static ProductEntity pizzaEntity = new ProductEntity(
                null, "Pizza", 20000, "FOOD", "pizza@test"
        );
    }
}
