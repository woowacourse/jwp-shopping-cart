package cart.cart.service;

import cart.cart.dto.AuthInfo;
import cart.cart.dto.CartResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class CartServiceTest {

    private static final String EMAIL = "rg970604@naver.com";
    private static final String PASSWORD = "password";

    @Autowired
    private CartService cartService;

    @Test
    void 개별_장바구니_조회() {
        AuthInfo authInfo = new AuthInfo(EMAIL, PASSWORD);
        List<CartResponse> cartResponses = cartService.showCart(authInfo);

        CartResponse firstCartResponse = cartResponses.get(0);
        CartResponse secondCartResponse = cartResponses.get(1);

        assertThat(firstCartResponse.getId()).isEqualTo(1L);
        assertThat(secondCartResponse.getId()).isEqualTo(2L);

    }

    @Test
    void 장바구니_상품_추가() {
        AuthInfo authInfo = new AuthInfo(EMAIL, PASSWORD);
        cartService.addCart(1L, authInfo);

        List<CartResponse> cartResponses = cartService.showCart(authInfo);

        CartResponse cartResponse = cartResponses.get(2);
        assertThat(cartResponse.getId()).isEqualTo(3L);
    }

    @Test
    void 장바구니_상품_삭제() {
        AuthInfo authInfo = new AuthInfo(EMAIL, PASSWORD);
        cartService.deleteCartById(1L, authInfo);
        List<CartResponse> cartResponses = cartService.showCart(authInfo);
        assertThat(cartResponses.size()).isSameAs(1);
    }


}