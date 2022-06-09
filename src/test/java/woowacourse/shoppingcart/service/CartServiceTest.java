package woowacourse.shoppingcart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.DeleteCartItemRequest;
import woowacourse.shoppingcart.dto.DeleteCartItemRequests;
import woowacourse.shoppingcart.dto.UpdateCartItemRequest;
import woowacourse.shoppingcart.dto.UpdateCartItemRequests;
import woowacourse.shoppingcart.exception.NotInCustomerCartItemException;

@SpringBootTest
@Sql("/init.sql")
public class CartServiceTest {

    @Autowired
    private CartService cartService;

    @BeforeEach
    void setUp() {
        var addProductRequest = new AddCartItemRequest(1L, 1, true);
        cartService.addCart(1L, addProductRequest);
    }

    @Test
    void 장바구니_상품_추가() {
        var findAllCartItemResponse = cartService.getAllCartItem(1L);
        var findCartItemResponse = findAllCartItemResponse.getCartItems().get(0);

        assertAll(
                () -> assertThat(findCartItemResponse.getId()).isEqualTo(1L),
                () -> assertThat(findCartItemResponse.getName()).isEqualTo("water"),
                () -> assertThat(findCartItemResponse.getPrice()).isEqualTo(1000),
                () -> assertThat(findCartItemResponse.getImageUrl()).isEqualTo("image_url"),
                () -> assertThat(findCartItemResponse.getQuantity()).isEqualTo(1),
                () -> assertThat(findCartItemResponse.getChecked()).isTrue()
        );
    }

    @Test
    void 장바구니_선택_상품_제거() {
        var deleteCartItemRequest = new DeleteCartItemRequest(1L);
        cartService.deleteCart(1L, new DeleteCartItemRequests(List.of(deleteCartItemRequest)));

        var findAllCartItemResponse = cartService.getAllCartItem(1L);
        assertThat(findAllCartItemResponse.getCartItems().size()).isEqualTo(0);
    }

    @Test
    void 유저가_장바구니_아이템_아이디를_가지고_있지_않는_경우() {
        var invalidCartItemId = 2L;
        var deleteCartItemRequest = new DeleteCartItemRequest(invalidCartItemId);

        assertThatThrownBy(() -> cartService.deleteCart(1L,
                new DeleteCartItemRequests(List.of(deleteCartItemRequest))))
                .isInstanceOf(NotInCustomerCartItemException.class);
    }

    @Test
    void 장바구니_전체_상품_제거() {
        cartService.deleteAll(1L);

        var findAllCartItemResponse = cartService.getAllCartItem(1L);
        assertThat(findAllCartItemResponse.getCartItems().size()).isEqualTo(0);

    }

    @Test
    void 장바구니_상품_정보_수정() {
        var updateCartItemRequest = new UpdateCartItemRequest(1L, 3, false);
        var updateCartItemsRequest = new UpdateCartItemRequests(List.of(updateCartItemRequest));

        cartService.update(1L, updateCartItemsRequest);

        var allCartItem = cartService.getAllCartItem(1L);
        var cartItem = allCartItem.getCartItems().get(0);

        assertAll(
                () -> assertThat(cartItem.getId()).isEqualTo(1L),
                () -> assertThat(cartItem.getName()).isEqualTo("water"),
                () -> assertThat(cartItem.getPrice()).isEqualTo(1000),
                () -> assertThat(cartItem.getImageUrl()).isEqualTo("image_url"),
                () -> assertThat(cartItem.getQuantity()).isEqualTo(3),
                () -> assertThat(cartItem.getChecked()).isFalse()
        );
    }
}
