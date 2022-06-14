package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.CartRequest;
import woowacourse.shoppingcart.dto.CartResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;

@SpringBootTest
@Sql(scripts = "classpath:schema.sql")
@Transactional
class CartServiceTest {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CartService cartService;

    @DisplayName("수량을 0 이하로 상품을 장바구니에 담으면 예외가 발생한다.")
    @Test
    void addCartItemQuantityException() {
        saveCustomer();
        CartRequest savingCartItem = new CartRequest(1L, 0);

        assertThatThrownBy(() -> cartService.addCart(1L, savingCartItem))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바르지 않은 상품 수량 형식입니다.");
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 증가시킨다.")
    @Test
    void addCartItemQuantity() {
        saveCustomer();
        CartRequest savingCartItem = new CartRequest(1L, 5);
        cartService.addCart(1L, savingCartItem);

        CartRequest updatingCartItem = new CartRequest(1L, 10);
        cartService.updateCartQuantity(1L, updatingCartItem);

        List<CartResponse> cartProductsByCustomerId = cartService.findCartProductsByCustomerId(1L);

        assertThat(cartProductsByCustomerId.size()).isEqualTo(1);
        cartProductsByCustomerId.forEach(cart -> assertThat(cart.getQuantity()).isEqualTo(10));
    }

    @DisplayName("장바구니에 담긴 상품의 수량을 0이하로 감소시키면 예외가 발생한다.")
    @Test
    void updateCartItemQuantityException() {
        saveCustomer();
        CartRequest savingCartItem = new CartRequest(1L, 5);
        cartService.addCart(1L, savingCartItem);

        CartRequest updatingCartItem = new CartRequest(1L, 0);

        assertThatThrownBy(() -> cartService.updateCartQuantity(1L, updatingCartItem))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("올바르지 않은 상품 수량 형식입니다.");
    }

    @DisplayName("customer가 동일한 상품을 담았을 경우 예외가 발생한다.")
    @Test
    void checkDuplicationCart() {
        saveCustomer();
        CartRequest savingCartItem = new CartRequest(1L, 5);
        cartService.addCart(1L, savingCartItem);

        assertThatThrownBy(() -> cartService.addCart(1L, savingCartItem))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("동일한 회원이 동일한 상품을 담았습니다.");
    }


    void saveCustomer() {
        CustomerRequest customer =
                new CustomerRequest("email", "Pw123456!", "name", "010-2222-3333", "address");
        customerService.save(customer);
    }
}
