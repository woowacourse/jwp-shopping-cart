package woowacourse.shoppingcart.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dao.CartItemDao;
import woowacourse.shoppingcart.domain.CartItem;
import woowacourse.shoppingcart.dto.*;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @Autowired
    private CartItemDao cartItemDao;

    @Test
    void 장바구니_조회() {
        CartResponse cartResponse = cartService.findByUserName("puterism");
        assertThat(cartResponse.getProducts().size()).isEqualTo(3);
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
    void 장바구니_내의_존재하는_상품의_수량을_변경하는_경우() {
        UpdateCartItemRequest updateCartItemRequest = new UpdateCartItemRequest(
                List.of(new UpdateCartItemElement(1L, 10, false),
                        new UpdateCartItemElement(2L, 1, true)));
        CartResponse updateCartItemResponse =
                cartService.updateItem("puterism", updateCartItemRequest);
        assertThat(updateCartItemResponse.getProducts().size()).isEqualTo(2);
    }
}
