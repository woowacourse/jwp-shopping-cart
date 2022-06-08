package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;
import static woowacourse.Fixture.치킨;
import static woowacourse.Fixture.페퍼_비밀번호;
import static woowacourse.Fixture.페퍼_아이디;
import static woowacourse.Fixture.페퍼_이름;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.CartItemCreateRequest;
import woowacourse.shoppingcart.dto.CartItemResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidProductException;

@SpringBootTest
@Transactional
@Nested
@DisplayName("CartItemService 클래스의")
class CartItemServiceTest {

    @Autowired
    private CartItemService cartItemService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Nested
    @DisplayName("add 메서드는")
    class add {
        @Test
        @DisplayName("고객의 장바구니에 상품을 1개 추가한다.")
        void success() {
            // given
            customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            Long productId = productService.addProduct(치킨);
            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(productId);

            // when
            CartItemResponse response = cartItemService.add(new LoginCustomer(페퍼_아이디), cartItemCreateRequest);

            // then
            assertAll(
                    () -> assertThat(response.getProductId()).isEqualTo(productId),
                    () -> assertThat(response.getName()).isEqualTo(치킨.getName()),
                    () -> assertThat(response.getImageUrl()).isEqualTo(치킨.getImageUrl()),
                    () -> assertThat(response.getPrice()).isEqualTo(치킨.getPrice()),
                    () -> assertThat(response.getQuantity()).isEqualTo(1)
            );
        }

        @Test
        @DisplayName("상품이 존재하지 않는 경우, 예외를 던진다.")
        void product_notExist() {
            // given
            Long productId = productService.addProduct(치킨);
            productService.deleteProductById(productId);

            customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            LoginCustomer loginCustomer = new LoginCustomer(페퍼_아이디);
            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(productId);

            // when & then
            assertThatThrownBy(() -> cartItemService.add(loginCustomer, cartItemCreateRequest))
                    .isInstanceOf(InvalidProductException.class);
        }

        @Test
        @DisplayName("고객의 장바구니에 이미 상품이 존재하는 경우, 예외를 던진다.")
        void cartItem_alreadyExist() {
            // given
            customerService.save(new CustomerRequest(페퍼_아이디, 페퍼_이름, 페퍼_비밀번호));
            LoginCustomer loginCustomer = new LoginCustomer(페퍼_아이디);

            Long productId = productService.addProduct(치킨);
            CartItemCreateRequest cartItemCreateRequest = new CartItemCreateRequest(productId);
            cartItemService.add(loginCustomer, cartItemCreateRequest);

            // when & then
            assertThatThrownBy(() -> cartItemService.add(loginCustomer, cartItemCreateRequest))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("이미 장바구니에 상품이 존재합니다.");
        }
    }
}
