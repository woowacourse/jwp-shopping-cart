package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:import.sql"})
class CartServiceTest {

    @Autowired
    private CartService cartService;

    @DisplayName("상품 재고보다 더 많은 수량을 구매하려고 하면 예외를 발생시킨다.")
    @Test
    void addCartItem_InvalidPurchasingQuantity() {
        assertThatThrownBy(() -> cartService.addCart(1L, 1L, 101))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("상품 재고가 부족합니다.");
    }
}