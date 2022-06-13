package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dto.customer.CustomerSignUpRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("유저를 저장한다")
    void save() {
        CustomerSignUpRequest request = new CustomerSignUpRequest("username", "password123", "01012345678", "성담빌딩");
        assertDoesNotThrow(() -> customerService.save(request));
    }

    @Test
    @DisplayName("이미 존재하는 유저 이름이면 예외 발생")
    void save_duplicatedUserName_throwException() {
        CustomerSignUpRequest request = new CustomerSignUpRequest("username", "password123", "01012345678", "성담빌딩");
        customerService.save(request);

        assertThatThrownBy(() -> customerService.save(request))
                .isInstanceOf(InvalidCustomerException.class)
                .hasMessage("이미 존재하는 유저 이름입니다.");
    }
}
