package woowacourse.shoppingcart.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.shoppingcart.application.ProductFixture.바나나;
import static woowacourse.shoppingcart.application.ProductFixture.사과;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.CartItem;

@JdbcTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartItemDaoTest {
    private final CartItemDao cartItemDao;
    private final ProductDao productDao;

    private long bananaId;
    private long appleId;

    public CartItemDaoTest(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        cartItemDao = new CartItemDao(namedParameterJdbcTemplate);
        productDao = new ProductDao(namedParameterJdbcTemplate);
    }

    @BeforeEach
    void setUp() {
        bananaId = productDao.save(바나나).getId();
        appleId = productDao.save(사과).getId();
    }

    @DisplayName("카트에 아이템을 담으면, 담긴 카트 아이디를 반환한다. ")
    @Test
    void addCartItem() {

        // given
        final Long customerId = 1L;

        // when
        final Long cartId = cartItemDao.addCartItem(customerId, bananaId);

        // then
        assertThat(cartId).isEqualTo(1L);
    }

    @DisplayName("커스터머 아이디를 넣으면, 해당 커스터머가 구매한 상품의 아이디 목록을 가져온다.")
    @Test
    void findProductIdsByCustomerId() {

        // given
        final Long customerId = 1L;
        cartItemDao.addCartItem(customerId, bananaId);
        cartItemDao.addCartItem(customerId, appleId);
        // when
        final List<Long> productsIds = cartItemDao.findProductIdsByCustomerId(customerId);

        // then
        assertThat(productsIds).containsExactly(1L, 2L);
    }

    @DisplayName("Customer Id를 넣으면, 해당 장바구니 Id들을 가져온다.")
    @Test
    void findIdsByCustomerId() {

        // given
        final Long customerId = 1L;
        cartItemDao.addCartItem(customerId, bananaId);
        cartItemDao.addCartItem(customerId, appleId);
        // when
        final List<Long> cartIds = cartItemDao.findIdsByCustomerId(customerId);

        // then
        assertThat(cartIds).containsExactly(1L, 2L);
    }

    @DisplayName("cart Id에 따라 cart 를 삭제한다.")
    @Test
    void deleteCartItem() {

        // given
        final Long customerId = 1L;
        cartItemDao.addCartItem(customerId, bananaId);
        cartItemDao.addCartItem(customerId, appleId);
        // when
        cartItemDao.deleteCartItem(customerId, bananaId);

        // then
        final List<Long> productIds = cartItemDao.findProductIdsByCustomerId(customerId);

        assertThat(productIds.size()).isEqualTo(1);
    }

    @Test
    void deleteAllCartItems() {
        //given
        final Long customerId = 1L;
        cartItemDao.addCartItem(customerId, bananaId);
        cartItemDao.addCartItem(customerId, appleId);
        //when
        int affectedQuery = cartItemDao.deleteAllCartItems(customerId, List.of(bananaId, appleId));
        //then
        assertThat(affectedQuery).isEqualTo(2);
    }

    @Test
    void findCartItemsByCustomerId() {
        //given
        final Long customerId = 1L;
        cartItemDao.addCartItem(customerId, bananaId);
         cartItemDao.addCartItem(customerId, appleId);
        List<CartItem> cartItems = cartItemDao.findCartItemsByCustomerId(customerId);
        //when

        //then
        assertAll(
                () -> assertThat(cartItems).hasSize(2),
                () -> assertThat(cartItems.get(0).getProductId()).isEqualTo(1L),
                () -> assertThat(cartItems.get(0).getProductName()).isEqualTo("바나나"),
                () -> assertThat(cartItems.get(0).getProductPrice()).isEqualTo(1000),
                () -> assertThat(cartItems.get(1).getProductId()).isEqualTo(2L),
                () -> assertThat(cartItems.get(1).getProductName()).isEqualTo("사과"),
                () -> assertThat(cartItems.get(1).getProductPrice()).isEqualTo(1500)
        );
    }
}
