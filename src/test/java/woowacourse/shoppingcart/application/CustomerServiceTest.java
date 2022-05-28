package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@SuppressWarnings("NonAsciiChracters")
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql("classpath:schema.sql")
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @DisplayName("addCustomer 메서드는 회원을 가입한다.")
    @Nested
    class AddCustomerTest {

        @Test
        void 중복되지_않은_아이디일_경우_성공() {
            CustomerRequest customerRequest = new CustomerRequest("angie", "angel", "12345678aA!");

            CustomerResponse actual = customerService.addCustomer(customerRequest);

            assertThat(actual).extracting("loginId", "username")
                    .containsExactly("angie", "angel");
        }

        @Test
        void 중복되는_아이디일_경우_예외발생() {
            CustomerRequest customerRequest = new CustomerRequest("angie", "angel", "12345678aA!");

            customerService.addCustomer(customerRequest);

            assertThatThrownBy(() -> customerService.addCustomer(customerRequest))
                    .isInstanceOf(IllegalArgumentException.class);
        }
    }
}