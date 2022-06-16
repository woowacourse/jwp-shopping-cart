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
import woowacourse.shoppingcart.dto.request.CustomerPasswordRequest;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.request.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.request.LoginCustomer;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.util.HashTool;

@SuppressWarnings("NonAsciiChracters")
@SpringBootTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Sql(scripts = {"classpath:schema.sql", "classpath:data.sql"})
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @DisplayName("addCustomer 메서드는 회원을 가입한다.")
    @Nested
    class AddCustomerTest {

        @Test
        void 중복되지_않은_아이디일_경우_성공() {
            CustomerRequest customerRequest = new CustomerRequest("angie@gmail.com", "angel", "12345678aA!");

            CustomerResponse actual = customerService.addCustomer(customerRequest);

            assertThat(actual).extracting("loginId", "name")
                    .containsExactly("angie@gmail.com", "angel");
        }

        @Test
        void 중복되는_아이디일_경우_예외발생() {
            CustomerRequest customerRequest = new CustomerRequest("angie@gmail.com", "angel", "12345678aA!");

            customerService.addCustomer(customerRequest);

            assertThatThrownBy(() -> customerService.addCustomer(customerRequest))
                    .isInstanceOf(DuplicateCustomerException.class);
        }
    }

    @DisplayName("updateCustomer 메서드는 회원정보를 수정한다.")
    @Nested
    class UpdateCustomerTest {

        @Test
        void 정상적인_데이터가_들어올_경우_성공() {
            CustomerRequest customerRequest = new CustomerRequest("angie@gmail.com", "angel", "12345678aA!");
            customerService.addCustomer(customerRequest);

            CustomerUpdateRequest updateCustomerRequest = new CustomerUpdateRequest("seungpapang", "12345678aA!");
            LoginCustomer loginCustomer = new LoginCustomer(26L, "angie@gmail.com", "angel", HashTool.hashing("12345678aA!"));

            CustomerResponse actual = customerService.updateCustomer(updateCustomerRequest, loginCustomer);

            assertThat(actual).extracting("loginId", "name")
                    .containsExactly("angie@gmail.com", "seungpapang");
        }

        @Test
        void 이미_존재하는_유저네임인_경우_예외발생() {
            CustomerRequest customerRequest = new CustomerRequest("angie@gmail.com", "angel", "12345678aA!");
            customerService.addCustomer(customerRequest);

            CustomerRequest updateCustomerRequest = new CustomerRequest("angie@gmail.com", "angel", "12345678aA!");

            assertThatThrownBy(() -> customerService.addCustomer(updateCustomerRequest))
                    .isInstanceOf(DuplicateCustomerException.class);
        }
    }

    @DisplayName("deletedCustomer 메서드는 회원 탈퇴를 한다.")
    @Nested
    class deletedCustomerTest {

        @Test
        void 정상적인_데이터가_들어올_경우_성공() {
            CustomerRequest customerRequest = new CustomerRequest("angie@gmail.com", "angel", "12345678aA!");
            customerService.addCustomer(customerRequest);

            CustomerPasswordRequest customerPasswordRequest = new CustomerPasswordRequest("12345678aA!");
            LoginCustomer loginCustomer = new LoginCustomer(26L, "angie@gmail.com", "angel", HashTool.hashing("12345678aA!"));

            assertThatCode(() -> customerService.deleteCustomer(customerPasswordRequest, loginCustomer))
                .doesNotThrowAnyException();
        }

        @Test
        void 존재하지_않는_회원일_경우_예외발생() {
            CustomerPasswordRequest customerPasswordRequest = new CustomerPasswordRequest(
                "12345678aA!");
            LoginCustomer loginCustomer = new LoginCustomer(9999L, "angie@gmail.com", "angel", HashTool.hashing("12345678aA!"));

            assertThatThrownBy(() -> customerService.deleteCustomer(customerPasswordRequest, loginCustomer))
                .isInstanceOf(InvalidCustomerException.class);
        }
    }

}
