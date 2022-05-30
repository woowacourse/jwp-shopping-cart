package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;

@SpringBootTest
@Transactional
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @DisplayName("정상적으로 회원 등록")
    @Test
    void addCustomer() {
        CustomerRequest request = new CustomerRequest("tonic@email.com", "12345678a", "토닉");
        Long customerId = customerService.registCustomer(request);
        Customer customer = customerService.findById(customerId);
        assertThat(customer).isNotNull();
    }

    @DisplayName("중복된 email로 회원 등록")
    @Test
    void duplicatedEmailCustomer() {
        CustomerRequest request = new CustomerRequest("tonic@email.com", "12345678a", "토닉");
        customerService.registCustomer(request);

        assertThatThrownBy(() -> customerService.registCustomer(request))
                .isInstanceOf(DuplicateCustomerException.class);
    }
}
