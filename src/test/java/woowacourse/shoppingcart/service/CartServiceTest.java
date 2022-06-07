package woowacourse.shoppingcart.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.application.CartService;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.exception.InvalidCartItemException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
public class CartServiceTest {

    @Autowired
    private CartService cartService;

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
}
