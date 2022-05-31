package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import woowacourse.shoppingcart.dto.CustomerPasswordRequest;
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

    @DisplayName("updateCustomer 메서드는 회원정보를 수정한다.")
    @Nested
    class UpdateCustomerTest {

        @Test
        void 정상적인_데이터가_들어올_경우_성공() {
            CustomerRequest customerRequest = new CustomerRequest("angie", "angel", "12345678aA!");
            customerService.addCustomer(customerRequest);

            CustomerRequest updateCustomerRequest = new CustomerRequest("angie", "seungpapang", "12345678aA!");

            CustomerResponse actual = customerService.updateCustomer(updateCustomerRequest);

            assertThat(actual).extracting("loginId", "username")
                .containsExactly("angie", "seungpapang");
        }

        @Test
        void 비밀번호가_일치하지_않는_경우_예외발생() {
            CustomerRequest customerRequest = new CustomerRequest("angie", "angel", "12345678aA!");
            customerService.addCustomer(customerRequest);

            CustomerRequest updateCustomerRequest = new CustomerRequest("angie", "seungpapang", "invalidPassword");

            assertThatThrownBy(() -> customerService.updateCustomer(updateCustomerRequest))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 존재하지_않는_회원일_경우_예외발생() {
            CustomerRequest customerRequest = new CustomerRequest("angie", "angel", "12345678aA!");

            assertThatThrownBy(() -> customerService.updateCustomer(customerRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
        }

        @Test
        void 이미_존재하는_유저네임인_경우_예외발생() {
            CustomerRequest customerRequest = new CustomerRequest("angie", "angel", "12345678aA!");
            customerService.addCustomer(customerRequest);

            CustomerRequest updateCustomerRequest = new CustomerRequest("angie", "angel", "12345678aA!");

            assertThatThrownBy(() -> customerService.addCustomer(updateCustomerRequest))
                .isInstanceOf(IllegalArgumentException.class);
        }
    }

    @DisplayName("deletedCustomer 메서드는 회원 탈퇴를 한다.")
    @Nested
    class deletedCustomerTest {

        @Test
        void 비밀번호가_일치할_경우_탈퇴_성공() {
            CustomerRequest customerRequest = new CustomerRequest("angie", "angel", "12345678aA!");
            customerService.addCustomer(customerRequest);

            CustomerPasswordRequest customerPasswordRequest = new CustomerPasswordRequest("12345678aA!");

            assertThatCode(() -> customerService.deleteCustomer("angie", customerPasswordRequest))
                .doesNotThrowAnyException();
        }

        @Test
        void 비밀번호가_일치하지_않는_경우_예외발생() {
            CustomerRequest customerRequest = new CustomerRequest("angie", "angel", "12345678aA!");
            customerService.addCustomer(customerRequest);

            CustomerPasswordRequest invalidPassword = new CustomerPasswordRequest("invalidPassword");

            assertThatThrownBy(() -> customerService.deleteCustomer("angie", invalidPassword))
                .isInstanceOf(IllegalArgumentException.class);
        }

        @Test
        void 존재하지_않는_회원일_경우_예외발생() {
            CustomerRequest customerRequest = new CustomerRequest("angie", "angel", "12345678aA!");
            customerService.addCustomer(customerRequest);

            CustomerPasswordRequest customerPasswordRequest = new CustomerPasswordRequest("12345678aA!");

            assertThatThrownBy(() -> customerService.deleteCustomer("seungpapang", customerPasswordRequest))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("존재하지 않는 회원입니다.");
        }
    }
}
