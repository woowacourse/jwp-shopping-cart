package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import cart.dto.product.ProductResponse;
import cart.dto.user.UserRequest;

@SpringBootTest
class CartServiceTest {

    @Autowired
    CartService cartService;

    @Test
    @DisplayName("회원이 장바구니에 담아둔 모든 상품 정보를 반환한다.")
    void findAllProductsInCart() {
        final UserRequest user = new UserRequest("ahdjd5@gmail.com", "qwer1234");

        final List<ProductResponse> result = cartService.findAllProductsInCart(user);

        Assertions.assertAll(
                () -> assertThat(result).hasSize(1),
                () -> assertThat(result.get(0).getName()).isEqualTo("치킨"),
                () -> assertThat(result.get(0).getPrice()).isEqualTo(10000)
        );
    }
}
