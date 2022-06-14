package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestConstructor;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.member.exception.MemberNotFoundException;
import woowacourse.shoppingcart.domain.Cart;
import woowacourse.shoppingcart.dto.AddCartItemRequest;
import woowacourse.shoppingcart.dto.PutCartItemRequest;
import woowacourse.shoppingcart.exception.cart.NotInMemberCartException;
import woowacourse.shoppingcart.exception.product.ProductNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@DisplayName("CartService에서")
class CartServiceTest {

    private final CartService cartService;

    public CartServiceTest(CartService cartService) {
        this.cartService = cartService;
    }

    @DisplayName("findCartsByMemberId메서드는")
    @Nested
    class findCartsByMemberId {

        @DisplayName("id로 해당 사용자의 장바구니를 찾아 반환환다.")
        @Test
        void findCartsByMemberId() {
            List<Cart> carts = cartService.findCartsByMemberId(1L);
            assertThat(carts).isNotEmpty();
        }


        @DisplayName("유효하지 않은 사용자인 경우 예외가 발생한다.")
        @Test
        void notExistMemberId() {
            assertThatThrownBy(
                    () -> cartService.findCartsByMemberId(100L)
            ).isInstanceOf(MemberNotFoundException.class)
                    .hasMessageContaining("존재하지 않는 회원입니다.");
        }
    }

    @DisplayName("addCartItem메서드는")
    @Nested
    class addCartItem {

        @DisplayName("장바구니에 해당상품이 존재하지 않는다면 새롭게 추가한다.")
        @Test
        void addCartItem() {
            cartService.addCartItem(5L, new AddCartItemRequest(10L));

            Optional<Cart> cart = cartService.findCartsByMemberId(5L)
                    .stream()
                    .filter(c -> c.getProductId() == 10L)
                    .findFirst();

            assertThat(cart).isNotEmpty();
            assertThat(cart.get().getQuantity()).isEqualTo(1);
        }

        @DisplayName("장바구니에 해당 상품이 존재한다면 수량만 추가한다.")
        @Test
        void alreadyExistProduct() {
            // given
            cartService.addCartItem(2L, new AddCartItemRequest(1L));
            int beforeQuantity = cartService.findCartsByMemberId(2L).get(0).getQuantity();

            // when
            cartService.addCartItem(2L, new AddCartItemRequest(1L));
            int afterQuantity = cartService.findCartsByMemberId(2L).get(0).getQuantity();

            // then
            assertThat(afterQuantity).isEqualTo(beforeQuantity + 1);
        }

        @DisplayName("유효하지 않은 사용자인 경우 예외가 발생한다.")
        @Test
        void notExistMemberId() {
            assertThatThrownBy(
                    () -> cartService.addCartItem(100L, new AddCartItemRequest(1L))
            ).isInstanceOf(MemberNotFoundException.class)
                    .hasMessageContaining("존재하지 않는 회원입니다.");
        }

        @DisplayName("유효하지 않은 상품인 경우 예외가 발생한다.")
        @Test
        void notExistProductId() {
            assertThatThrownBy(
                    () -> cartService.addCartItem(1L, new AddCartItemRequest(99L))
            ).isInstanceOf(ProductNotFoundException.class)
                    .hasMessageContaining("존재하지 않는 상품입니다.");
        }
    }


    @DisplayName("updateCartItem메서드는")
    @Nested
    class updateCartItem {

        @DisplayName("수량을 수정한다.")
        @Test
        void updateCartItem() {
            cartService.updateCartItem(1L, 1L, new PutCartItemRequest(50));

            Optional<Cart> cart = cartService.findCartsByMemberId(1L)
                    .stream()
                    .filter(c -> c.getId() == 1L)
                    .findFirst();

            assertThat(cart).isNotEmpty();
            assertThat(cart.get().getQuantity()).isEqualTo(50);
        }

        @DisplayName("유효하지 않은 사용자인 경우 예외가 발생한다.")
        @Test
        void notExistMemberId() {
            assertThatThrownBy(
                    () -> cartService.updateCartItem(100L, 1L, new PutCartItemRequest(10))
            ).isInstanceOf(MemberNotFoundException.class)
                    .hasMessageContaining("존재하지 않는 회원입니다.");
        }

        @DisplayName("사용자의 장바구니가 아닌 경우 예외가 발생한다.")
        @Test
        void wrongMemberCart() {
            assertThatThrownBy(
                    () -> cartService.updateCartItem(1L, 10L, new PutCartItemRequest(10))
            ).isInstanceOf(NotInMemberCartException.class)
                    .hasMessageContaining("해당 사용자의 유효한 장바구니가 아닙니다.");
        }
    }

    @DisplayName("deleteCart메서드는")
    @Nested
    class deleteCart {

        @DisplayName("장바구니를 삭제한다.")
        @Test
        void deleteCart() {
            cartService.deleteCart(1L, 1L);

            Optional<Cart> cart = cartService.findCartsByMemberId(1L)
                    .stream()
                    .filter(c -> c.getId() == 1L)
                    .findFirst();

            assertThat(cart).isEmpty();
        }

        @DisplayName("유효하지 않은 사용자인 경우 예외가 발생한다.")
        @Test
        void notExistMemberId() {
            assertThatThrownBy(
                    () -> cartService.deleteCart(100L, 1L)
            ).isInstanceOf(MemberNotFoundException.class)
                    .hasMessageContaining("존재하지 않는 회원입니다.");
        }

        @DisplayName("사용자의 장바구니가 아닌 경우 예외가 발생한다.")
        @Test
        void wrongMemberCart() {
            assertThatThrownBy(
                    () -> cartService.deleteCart(1L, 10L)
            ).isInstanceOf(NotInMemberCartException.class)
                    .hasMessageContaining("해당 사용자의 유효한 장바구니가 아닙니다.");
        }
    }
}
