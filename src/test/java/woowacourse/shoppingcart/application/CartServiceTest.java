package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.CartAdditionRequest;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class CartServiceTest {

    public static final String EMAIL = "email@email.com";

    private final CartService cartService;

    @Autowired
    public CartServiceTest(CartService cartService) {
        this.cartService = cartService;
    }

    @DisplayName("장바구니를 조회한다.")
    @Test
    void findCartsByEmail() {
        assertThat(cartService.findCartsByEmail(EMAIL)).hasSize(2);
    }

    @DisplayName("장바구니에 제품을 추가한다.")
    @Test
    void addCart() {
        CartAdditionRequest cartAdditionRequest = new CartAdditionRequest(1L, 1);
        assertDoesNotThrow(() -> cartService.addCart(cartAdditionRequest, EMAIL));
    }
}
