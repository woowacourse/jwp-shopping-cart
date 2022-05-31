package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.shoppingcart.application.dto.CustomerSaveRequest;

@SpringBootTest
@Transactional
@Sql(scripts = {"classpath:schema.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    @DisplayName("회원을 저장한다.")
    void saveCustomer() {
        // given
        CustomerSaveRequest request = new CustomerSaveRequest("email@email.com", "password1234A!", "rookie");

        // when
        Long customerId = customerService.save(request);

        // then
        CustomerResponse response = customerService.findById(customerId);
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(new CustomerResponse(1L, "email@email.com", "rookie"));
    }

    @Test
    @DisplayName("이미 등록된 이메일로 가입할 경우 예외가 발생한다.")
    void saveCustomerDuplicateEmail() {
        // given
        CustomerSaveRequest request = new CustomerSaveRequest("email@email.com", "password1234A!", "rookie");
        customerService.save(request);

        // when & then
        assertThatThrownBy(() -> customerService.save(new CustomerSaveRequest("email@email.com", "password1234A!", "zero")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("이미 등록된 닉네임으로 가입할 경우 예외가 발생한다.")
    void saveCustomerDuplicateNickname() {
        // given
        CustomerSaveRequest request = new CustomerSaveRequest("email1@email.com", "password1234A!", "rookie");
        customerService.save(request);

        // when & then
        assertThatThrownBy(() -> customerService.save(new CustomerSaveRequest("email2@email.com", "password1234A!", "rookie")))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
