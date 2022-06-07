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
import woowacourse.auth.dto.TokenRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;

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
        List<CartItemResponse> cartItemsByCustomerId = cartItemService.findCartItemsByCustomerId(tokenRequest);

        // then
        assertAll(
                () -> assertThat(cartItemsByCustomerId.size()).isEqualTo(3),
                () -> assertThat(cartItemsByCustomerId.stream().map(CartItemResponse::getProductId).collect(Collectors.toList()))
                        .containsExactly(1L, 2L, 3L),
                () -> assertThat(cartItemsByCustomerId.stream().map(CartItemResponse::getQuantity).collect(Collectors.toList()))
                        .containsExactly(5, 7, 9)
        );
    }
}
