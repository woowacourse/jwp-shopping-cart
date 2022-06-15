package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql", "classpath:products.sql"})
class CartServiceTest {

    private static final String 이메일 = "email@email.com";
    private static final CartRequest 장바구니_상품_추가_요청 = new CartRequest(1L, 1);

    private final CartService cartService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CartServiceTest(CartService cartService, JdbcTemplate jdbcTemplate) {
        this.cartService = cartService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @DisplayName("이메일에 해당하는 장바구니 목록을 반환한다.")
    @Test
    void findCartsByEmail() {
        cartService.addCart(장바구니_상품_추가_요청, 이메일);

        List<Cart> carts = cartService.findCartsByEmail(이메일);

        assertAll(
                () -> assertThat(carts.size()).isEqualTo(1),
                () -> assertThat(carts.get(0).getProductId()).isEqualTo(1L),
                () -> assertThat(carts.get(0).getName()).isEqualTo("캠핑 의자"),
                () -> assertThat(carts.get(0).getPrice()).isEqualTo(35000),
                () -> assertThat(carts.get(0).getQuantity()).isEqualTo(1)
        );
    }

    @DisplayName("이메일에 해당하는 장바구니에 상품을 추가한다.")
    @Test
    void addCart() {
        Long cartId = cartService.addCart(장바구니_상품_추가_요청, 이메일);

        assertThat(cartId).isEqualTo(1L);
    }

    @DisplayName("이미 장바구니에 존재하는 상품에 상품을 더 추가한다.")
    @Test
    void addAnotherCart() {
        Long cartId = cartService.addCart(장바구니_상품_추가_요청, 이메일);
        cartService.addCart(new CartRequest(1L, 10), 이메일);

        List<Cart> carts = cartService.findCartsByEmail(이메일);

        int quantity = carts.stream()
                .filter(cart -> cart.getId().equals(cartId))
                .findAny()
                .get()
                .getQuantity();

        assertThat(quantity).isEqualTo(11);
    }

    @DisplayName("이미 장바구니에 존재하는 상품에 상품을 더 추가할 때 재고가 부족할 경우 예외가 발생한다.")
    @Test
    void addAnotherCartNotEnough() {
        cartService.addCart(장바구니_상품_추가_요청, 이메일);

        assertThatThrownBy(() -> cartService.addCart(new CartRequest(1L, 100), 이메일))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessage("상품의 수량이 부족합니다. 현재 재고 = 100");
    }

    @DisplayName("이메일에 해당하는 장바구니 상품의 수량을 변경한 후 목록을 반환한다.")
    @Test
    void modifyCartQuantity() {
        cartService.addCart(장바구니_상품_추가_요청, 이메일);

        CartRequest modifyRequest = new CartRequest(1L, 5);
        List<Cart> carts = cartService.modifyCartQuantity(modifyRequest, 이메일);

        assertAll(
                () -> assertThat(carts.size()).isEqualTo(1),
                () -> assertThat(carts.get(0).getProductId()).isEqualTo(1L),
                () -> assertThat(carts.get(0).getName()).isEqualTo("캠핑 의자"),
                () -> assertThat(carts.get(0).getPrice()).isEqualTo(35000),
                () -> assertThat(carts.get(0).getQuantity()).isEqualTo(5)
        );
    }

    @DisplayName("이메일에 해당하는 장바구니 상품을 제거한 후 목록을 반환한다.")
    @Test
    void deleteCart() {
        cartService.addCart(장바구니_상품_추가_요청, 이메일);

        List<Cart> carts = cartService.deleteCart(이메일, 1L);

        assertThat(carts).isEmpty();
    }

    @DisplayName("이메일에 해당하는 장바구니 상품을 제거한 후 목록을 반환한다.")
    @Test
    void deleteCartFail() {
        cartService.addCart(장바구니_상품_추가_요청, 이메일);

        assertThatThrownBy(() -> cartService.deleteCart(이메일, 2L))
                .isInstanceOf(NotInCustomerCartItemException.class);
    }
}