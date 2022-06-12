package woowacourse.shoppingcart.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.*;
import woowacourse.shoppingcart.exception.InvalidCartItemException;
import woowacourse.shoppingcart.exception.InvalidInformationException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql(scripts = {"classpath:testSchema.sql", "classpath:testCartData.sql"})
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemDao cartItemDao;

    @Test
    void 장바구니_조회() {
        CartResponse cartResponse = cartService.findByUserName("puterism");
        assertThat(cartResponse.getCartItems().size()).isEqualTo(3);
    }

    @Test
    void 존재하지_않는_사용자의_장바구니_조회() {
        assertThatThrownBy(() -> cartService.findByUserName("alpha"))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessage("[ERROR] 존재하지 않는 장바구니입니다.");
    }

    @Test
    void 장바구니_내의_존재하는_상품을_추가로_담는_경우() {
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(1L, 3, true);
        cartService.addItem("puterism", addCartItemRequest);
        CartItem actual = cartItemDao.findCartItemByIds(1L, 1L);
        CartItem expected = new CartItem(1L, 1L, 1L, 4, true);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 장바구니_내의_존재하지_않는_상품을_추가로_담는_경우() {
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(2L, 3, true);
        cartService.addItem("puterism", addCartItemRequest);
        CartItem actual = cartItemDao.findCartItemByIds(1L, 4L);
        CartItem expected = new CartItem(4L, 1L, 2L, 3, true);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void 존재하지_않는_사용자의_장바구니의_상품을_추가하는_경우() {
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(1L, 1, true);
        assertThatThrownBy(() -> cartService.addItem("alpha", addCartItemRequest))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessage("[ERROR] 존재하지 않는 장바구니입니다.");
    }

    @Test
    void 존재하지_않는_상품을_장바구니에_추가하는_경우() {
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(100L, 1, true);
        assertThatThrownBy(() -> cartService.addItem("puterism", addCartItemRequest))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessage("[ERROR] 존재하는 상품이 아닙니다.");
    }

    @Test
    void 자연수가_아닌_상품_id로_장바구니에_추가하는_경우() {
        AddCartItemRequest addCartItemRequest = new AddCartItemRequest(0L, 1, true);
        assertThatThrownBy(() -> cartService.addItem("puterism", addCartItemRequest))
                .isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 상품 ID는 자연수여야 합니다.");
    }

    @Test
    void 장바구니_내의_존재하는_상품의_수량을_변경하는_경우() {
        UpdateCartItemRequest updateCartItemRequest = new UpdateCartItemRequest(
                List.of(new UpdateCartItemElement(1L, 10, false),
                        new UpdateCartItemElement(2L, 1, true)));
        CartResponse updateCartItemResponse =
                cartService.updateItem("puterism", updateCartItemRequest);
        assertThat(updateCartItemResponse.getCartItems().size()).isEqualTo(2);
    }

    @Test
    void 유효하지_않은_항목의_수량을_변경하는_경우() {
        UpdateCartItemRequest updateCartItemRequest = new UpdateCartItemRequest(
                List.of(new UpdateCartItemElement(7L, 10, false),
                        new UpdateCartItemElement(2L, 1, true)));
        assertThatThrownBy(
                () -> cartService.updateItem("puterism", updateCartItemRequest))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessage("[ERROR] 장바구니에 없는 상품이 있습니다.");
    }

    @Test
    void 자연수가_아닌_항목의_ID로_수량을_변경하는_경우() {
        UpdateCartItemRequest updateCartItemRequest = new UpdateCartItemRequest(
                List.of(new UpdateCartItemElement(0L, 10, false),
                        new UpdateCartItemElement(2L, 1, true)));
        assertThatThrownBy(
                () -> cartService.updateItem("puterism", updateCartItemRequest))
                .isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 항목 ID는 자연수여야 합니다.");
    }

    @Test
    void 자연수가_아닌_상품의_수량으로_수량을_변경하는_경우() {
        UpdateCartItemRequest updateCartItemRequest = new UpdateCartItemRequest(
                List.of(new UpdateCartItemElement(1L, 0, false),
                        new UpdateCartItemElement(2L, 1, true)));
        assertThatThrownBy(
                () -> cartService.updateItem("puterism", updateCartItemRequest))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessage("[ERROR] 상품 수는 자연수여야 합니다.");
    }

    @Test
    void 장바구니_내의_존재하는_상품의_일부를_삭제하는_경우() {
        DeleteCartItemRequest deleteCartItemRequest = new DeleteCartItemRequest(
                List.of(
                        new DeleteCartItemElement(1L),
                        new DeleteCartItemElement(2L)));
        cartService.deleteItem("puterism", deleteCartItemRequest);
        CartResponse cartResponse = cartService.findByUserName("puterism");
        assertThat(cartResponse.getCartItems().size()).isEqualTo(1);
    }

    @Test
    void 자연수가_아닌_항목의_ID로_상품의_일부를_삭제하는_경우() {
        DeleteCartItemRequest deleteCartItemRequest = new DeleteCartItemRequest(
                List.of(
                        new DeleteCartItemElement(0L),
                        new DeleteCartItemElement(2L)));
        assertThatThrownBy(
                () -> cartService.deleteItem("puterism", deleteCartItemRequest))
                .isInstanceOf(InvalidInformationException.class)
                .hasMessage("[ERROR] 항목 ID는 자연수여야 합니다.");
    }

    @Test
    void 장바구니_내의_존재하지_않는_상품의_일부를_삭제하는_경우() {
        DeleteCartItemRequest deleteCartItemRequest = new DeleteCartItemRequest(
                List.of(
                        new DeleteCartItemElement(10L),
                        new DeleteCartItemElement(2L)));
        assertThatThrownBy(
                () -> cartService.deleteItem("puterism", deleteCartItemRequest))
                .isInstanceOf(InvalidCartItemException.class)
                .hasMessage("[ERROR] 장바구니에 없는 상품이 있습니다.");
    }

    @Test
    void 장바구니를_삭제하는_경우() {
        cartService.deleteCart("puterism");
        CartResponse cartResponse = cartService.findByUserName("puterism");
        assertThat(cartResponse.getCartItems().size()).isEqualTo(0);
    }
}
