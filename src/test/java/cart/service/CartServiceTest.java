package cart.service;

import static org.assertj.core.api.Assertions.assertThat;

import cart.service.dto.CartRequest;
import cart.service.dto.CartResponse;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @DisplayName("장바구니 상품을 저장하고 조회할 수 있다.")
    @Test
    @Sql("/cart_initialize.sql")
    void saveAndFindCartItems() {
        // given
        long productId  = 1L;
        long customerId = 1L;
        long cartId = cartService.save(new CartRequest(productId), customerId);

        // when
        List<CartResponse> cartItems = cartService.findAllByCustomerId(customerId);

        // then
        List<CartResponse> expectedItems = List.of(new CartResponse(cartId, "baron", "tempUrl", 2000));
        assertThat(cartItems).usingRecursiveComparison()
                .isEqualTo(expectedItems);
    }

}