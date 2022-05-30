package woowacourse.shoppingcart.application;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerSaveRequest;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerDao customerDao;

    @Test
    @DisplayName("회원을 등록한다.")
    void save() {
        // given
        CustomerSaveRequest customerSaveRequest = new CustomerSaveRequest("라라", "lala.seeun@gmail.com", "tpdmstpdms11");
        final Customer customer = new Customer(1L, customerSaveRequest.getName(), customerSaveRequest.getEmail(),
                customerSaveRequest.getPassword());
        Mockito.when(customerDao.save(Mockito.any(Customer.class)))
                .thenReturn(customer);

        // when, then
        Assertions.assertThatCode(() -> customerService.save(customerSaveRequest))
                .doesNotThrowAnyException();
    }
}
