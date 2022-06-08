package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.CartItemIdRequest;
import woowacourse.shoppingcart.dto.CartItemQuantityRequest;
import woowacourse.shoppingcart.dto.CartItemQuantityResponse;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.ProductIdRequest;
import woowacourse.shoppingcart.dto.TokenRequest;

@SpringBootTest
@Sql("/init.sql")
class CartItemServiceTest {

    @Autowired
    private CartItemService cartItemService;

    @DisplayName("조회된 장바구니 물품들의 정보를 반환하는 기능")
    @Test
    void findCartItemsByCustomerId() {
        // given
        TokenRequest tokenRequest = new TokenRequest(1L);

        // when
        List<CartItemResponse> responses = cartItemService.findCartItemsByCustomerId(tokenRequest);

        // then
        assertAll(
                () -> assertThat(responses.size()).isEqualTo(3),
                () -> assertThat(responses.stream().map(CartItemResponse::getProductId).collect(Collectors.toList()))
                        .containsExactly(1L, 2L, 3L),
                () -> assertThat(responses.stream().map(CartItemResponse::getQuantity).collect(Collectors.toList()))
                        .containsExactly(5, 7, 9)
        );
    }

    @DisplayName("사용자의 모든 장바구니 물품들의 수량 정보를 반환한다.")
    @Test
    void addCartItems() {
        // given
        TokenRequest tokenRequest = new TokenRequest(1L);
        List<ProductIdRequest> productIdRequests = List.of(new ProductIdRequest(3L), new ProductIdRequest(4L));

        // when
        List<CartItemQuantityResponse> responses = cartItemService.addCartItems(tokenRequest, productIdRequests);

        // then
        assertAll(
                () -> assertThat(responses.size()).isEqualTo(2),
                () -> assertThat(responses.get(0).getId()).isEqualTo(3L),
                () -> assertThat(responses.get(0).getQuantity()).isEqualTo(10),
                () -> assertThat(responses.get(1).getId()).isEqualTo(4L),
                () -> assertThat(responses.get(1).getQuantity()).isEqualTo(1)
        );
    }

    @DisplayName("수정된 장바구니 물품의 수량 정보를 반환한다.")
    @Test
    void updateCartItem() {
        // given
        TokenRequest tokenRequest = new TokenRequest(1L);
        CartItemQuantityRequest cartItemQuantityRequest = new CartItemQuantityRequest(1L, 100);

        // when
        CartItemQuantityResponse response = cartItemService.updateCartItem(tokenRequest, cartItemQuantityRequest);

        // then
        assertThat(response.getId()).isEqualTo(1L);
        assertThat(response.getQuantity()).isEqualTo(100);
    }

    @DisplayName("입력된 장바구니 물품의 id 들을 이용해서 저장된 나의 장바구니 물품을 삭제한다.")
    @Test
    void delete() {
        // given
        TokenRequest tokenRequest = new TokenRequest(1L);
        List<CartItemIdRequest> cartItemIdRequests = List.of(new CartItemIdRequest(1L), new CartItemIdRequest(2L));

        // when
        cartItemService.delete(cartItemIdRequests);

        // then
        List<CartItemResponse> responses = cartItemService.findCartItemsByCustomerId(tokenRequest);
        assertAll(
                () -> assertThat(responses.size()).isEqualTo(1),
                () -> assertThat(responses.get(0).getId()).isEqualTo(3L)
        );
    }
}
