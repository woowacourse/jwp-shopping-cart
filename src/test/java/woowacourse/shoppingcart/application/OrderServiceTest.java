package woowacourse.shoppingcart.application;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.request.CartItemRequest;
import woowacourse.shoppingcart.application.dto.request.CustomerIdentificationRequest;
import woowacourse.shoppingcart.application.dto.request.SignUpRequest;
import woowacourse.shoppingcart.exception.dataformat.QuantityDataFormatException;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql"})
@DisplayName("Order 서비스 테스트")
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @ParameterizedTest
    @ValueSource(ints = {0, -1})
    @DisplayName("상품 주문 시 주문할 개수가 1 미만이면 예외가 발생한다.")
    void addOrderQuantityException(int quantity) {
        // given
        SignUpRequest signUpRequest = new SignUpRequest("test@woowacourse.com", "test", "1234asdf!");
        Long customerId = customerService.signUp(signUpRequest);
        CustomerIdentificationRequest customerIdentificationRequest = new CustomerIdentificationRequest(String.valueOf(customerId));

        CartItemRequest cartItemRequest = new CartItemRequest(1L, quantity);

        // when & then
        assertThatThrownBy(() -> orderService.addOrder(customerIdentificationRequest, cartItemRequest))
                .isInstanceOf(QuantityDataFormatException.class)
                .hasMessage("수량은 1 이상이어야 합니다.");
    }
}
