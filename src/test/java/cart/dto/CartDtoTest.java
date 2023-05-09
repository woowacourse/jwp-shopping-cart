package cart.dto;

import cart.entity.Cart;
import cart.vo.Email;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CartDtoTest {

    @Test
    @DisplayName("CartDto -> Cart 로 변환할 때 잘 변환되는지 확인")
    void toEntity() {
        CartDto cartDto = new CartDto(Email.from("email@naver.com"), 1L);

        Cart cart = cartDto.toEntity();

        assertThat(cart.getEmail()).isEqualTo("email@naver.com");
        assertThat(cart.getProductId()).isEqualTo(1L);
    }

}
