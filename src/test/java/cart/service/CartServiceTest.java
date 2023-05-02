package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cart.dto.cart.CartProductResponse;
import cart.dto.user.UserRequest;

@SpringBootTest
class CartServiceTest {

    public static final UserRequest USER = new UserRequest("ahdjd5@gmail.com", "qwer1234");
    @Autowired
    CartService cartService;

    @Test
    @DisplayName("회원이 장바구니에 담아둔 모든 상품 정보를 반환한다.")
    void findAllProductsInCart() {
        final List<CartProductResponse> result = cartService.findAllProductsInCart(USER);

        Assertions.assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0).getName()).isEqualTo("치킨"),
                () -> assertThat(result.get(0).getPrice()).isEqualTo(10000)
        );
    }

    @Test
    @DisplayName("회원이 장바구니에 담아둔 상품을 삭제한다.")
    void removeProductInCart() {
        cartService.removeProductInCart(USER, 1L);

        final List<CartProductResponse> result = cartService.findAllProductsInCart(USER);

        assertThat(result.stream()
                .anyMatch(product -> product.getId() == 1L)
        )
                .isFalse();
    }
}
