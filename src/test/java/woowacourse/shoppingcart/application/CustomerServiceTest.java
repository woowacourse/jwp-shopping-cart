package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.ResponseStatus;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;

@SpringBootTest
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
}
