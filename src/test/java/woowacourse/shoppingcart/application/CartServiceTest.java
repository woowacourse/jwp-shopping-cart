package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;

import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.dao.ProductDao;
import woowacourse.shoppingcart.domain.cart.CartItem;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.ProductNotFoundException;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
public class CartServiceTest {

    private static final long CUSTOMER_ID = 1L;

    private final CartItemDao cartItemDao;
    private final ProductDao productDao;
    private final CartService cartService;

    public CartServiceTest(JdbcTemplate jdbcTemplate) {
        productDao = new ProductDao(jdbcTemplate);
        cartItemDao = new CartItemDao(jdbcTemplate);
        cartService = new CartService(cartItemDao, productDao);
    }

    @Test
    @DisplayName("장바구니에 제품을 추가한다.")
    void addCartItem() {
        //when
        Long savedId = cartService.addCartItem(1L, CUSTOMER_ID, 1);

        //then
        assertThat(cartItemDao.findIdsByCustomerId(CUSTOMER_ID)).containsExactly(savedId);
    }

    @Test
    @DisplayName("존재하지 않는 제품을 장바구니에 추가요청하면 예외를 던진다.")
    void addCartItem_productNotFound() {
        //when, then
        assertThatThrownBy(() -> cartService.addCartItem(99L, CUSTOMER_ID, 1))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("이미 추가된 제품을 장바구니에 추가요청하면 예외를 던진다.")
    void addCartItem_alreadyInStock() {
        //given
        cartService.addCartItem(1L, CUSTOMER_ID, 1);

        //when, then
        assertThatThrownBy(() -> cartService.addCartItem(1L, CUSTOMER_ID, 1))
            .isInstanceOf(InvalidCartItemException.class)
            .hasMessage("이미 담겨있는 상품입니다.");
    }

    @Test
    @DisplayName("제품 재고보다 많은 수량을 추가 요청하면 예외를 던진다.")
    void addCartItem_OverStock() {
        //when, then
        assertThatThrownBy(() -> cartService.addCartItem(1L, CUSTOMER_ID, 11))
            .isInstanceOf(InvalidCartItemException.class)
            .hasMessage("재고가 부족합니다.");
    }

    @Test
    @DisplayName("장바구니에 추가한 제품 목록을 조회한다.")
    void findCartItems() {
        //when
        cartItemDao.addCartItem(CUSTOMER_ID, getCartItem(1L, 1));
        cartItemDao.addCartItem(CUSTOMER_ID, getCartItem(2L, 1));

        List<CartItem> cartItems = cartService.findCartByCustomerId(CUSTOMER_ID).getValue();

        //then
        assertAll(
            () -> assertThat(cartItems.get(0).getId()).isEqualTo(1L),
            () -> assertThat(cartItems.get(1).getId()).isEqualTo(2L)
        );
    }

    private CartItem getCartItem(long productId, int count) {
        return new CartItem(count, productDao.findProductById(productId));
    }

    @Test
    @DisplayName("장바구니에 추가한 제품의 구매 수량을 수정한다.")
    void updateCount() {
        //when
        Long cartItemId = cartItemDao.addCartItem(CUSTOMER_ID, getCartItem(1L, 1));

        cartService.updateCount(CUSTOMER_ID, 1L, 2);

        //then
        assertThat(cartItemDao.findCountById(cartItemId)).isEqualTo(2);
    }

    @Test
    @DisplayName("제품 재고보다 많은 수량을 수정 요청하면 예외를 던진다.")
    void updateCount_OverStock() {
        //given
        cartItemDao.addCartItem(CUSTOMER_ID, getCartItem(1L, 1));

        //when, then
        assertThatThrownBy(() -> cartService.updateCount(1L, CUSTOMER_ID, 11))
            .isInstanceOf(InvalidCartItemException.class)
            .hasMessage("재고가 부족합니다.");
    }

    @Test
    @DisplayName("존재하지 않는 제품을 장바구니에 수정 요청하면 예외를 던진다.")
    void updateCount_productNotFound() {
        //given
        cartItemDao.addCartItem(CUSTOMER_ID, getCartItem(1L, 1));

        //when, then
        assertThatThrownBy(() -> cartService.updateCount(CUSTOMER_ID, 99L, 5))
            .isInstanceOf(ProductNotFoundException.class);
    }

    @Test
    @DisplayName("장바구니에 추가한 제품을 삭제한다.")
    void deleteCartItem() {
        //when
        cartItemDao.addCartItem(CUSTOMER_ID, getCartItem(1L, 1));
        cartService.deleteCartItem(CUSTOMER_ID, 1L);

        //then
        assertThat(cartItemDao.findIdsByCustomerId(CUSTOMER_ID)).hasSize(0);
    }

    @Test
    @DisplayName("존재하지 않는 제품을 장바구니에 삭제 요청하면 예외를 던진다.")
    void deleteCartItem_productNotFound() {
        //when, then
        assertThatThrownBy(() -> cartService.deleteCartItem(99L, CUSTOMER_ID))
            .isInstanceOf(ProductNotFoundException.class);
    }
}
