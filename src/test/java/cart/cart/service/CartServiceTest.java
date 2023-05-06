package cart.cart.service;

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
    private static final String CHICKEN_IMAGE = "https://nenechicken.com/17_new/images/menu/30005.jpg";
    private static final String PIZZA_IMAGE = "https://cdn.dominos.co.kr/admin/upload/goods/20230117_97ySneQn.jpg?RS=350x350&SP=1";
    private static final Long CHICKEN_PRICE = 18000L;
    private static final Long PIZZA_PRICE = 21000L;

    @Autowired
    private CartService cartService;

    @Test
    void 개별_장바구니_조회() {
        List<CartResponse> cartResponses = cartService.showCart(EMAIL, PASSWORD);

        CartResponse firstCartResponse = cartResponses.get(0);
        CartResponse sameWithFirstCartResponse = new CartResponse(1L, "치킨", CHICKEN_PRICE, CHICKEN_IMAGE);

        CartResponse secondCartResponse = cartResponses.get(1);
        CartResponse sameWithSecondCartResponse = new CartResponse(2L, "피자", PIZZA_PRICE, PIZZA_IMAGE);

        assertThat(firstCartResponse).isEqualTo(sameWithFirstCartResponse);
        assertThat(secondCartResponse).isEqualTo(sameWithSecondCartResponse);

    }

    @Test
    void 장바구니_상품_추가() {
        cartService.addCart(1L, EMAIL, PASSWORD);

        List<CartResponse> cartResponses = cartService.showCart(EMAIL, PASSWORD);

        CartResponse cartResponse = cartResponses.get(2);
        CartResponse sameCartResponse = new CartResponse(3L, "치킨", CHICKEN_PRICE, CHICKEN_IMAGE);
        assertThat(cartResponse).isEqualTo(sameCartResponse);
    }

    @Test
    void 장바구니_상품_삭제() {
        cartService.deleteCartById(1L, EMAIL, PASSWORD);

        List<CartResponse> cartResponses = cartService.showCart(EMAIL, PASSWORD);
        assertThat(cartResponses.size()).isSameAs(1);
    }


}