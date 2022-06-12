package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.shoppingcart.application.dto.EmailDuplicationResponse;
import woowacourse.shoppingcart.application.dto.UserNameDuplicationResponse;
import woowacourse.shoppingcart.exception.domain.CustomerNotFoundException;
import woowacourse.shoppingcart.exception.domain.DuplicateCustomerException;
import woowacourse.shoppingcart.ui.dto.CustomerRequest;
import woowacourse.shoppingcart.ui.dto.FindCustomerRequest;
import woowacourse.shoppingcart.ui.dto.UpdateCustomerRequest;
import woowacourse.support.test.ExtendedApplicationTest;

@ExtendedApplicationTest
class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    private Long id;

    @BeforeEach
    void setUp() {
        CustomerRequest customerRequest = new CustomerRequest(
            "some-name", "password12!@", "example@email.com", "address", "010-1234-1234"
        );
        id = customerService.createCustomer(customerRequest);
    }

    @DisplayName("사용자를 생성하면, 식별ID를 받는다.")
    @Test
    void createCustomer() {
        // given
        CustomerRequest request = new CustomerRequest(
            "createname", "password12!@", "creation@email.com", "address", "010-1234-1234"
        );
        // when
        final Long id = customerService.createCustomer(request);

        // then
        assertThat(id).isNotNull();
    }

    @DisplayName("중복된 정보로 저장하면 예외가 발생한다")
    @Test
    void throwsExceptionWithDuplicationOnSave() {
        // given
        CustomerRequest request = new CustomerRequest(
            "createname", "password12!@", "creation@email.com", "address", "010-1234-1234"
        );
        // when
        customerService.createCustomer(request);

        // then
        assertThatExceptionOfType(DuplicateCustomerException.class)
            .isThrownBy(() -> customerService.createCustomer(request));
    }

    @DisplayName("id가 들어있는 요청으로 사용자를 찾는다.")
    @Test
    void findCustomer() {
        // given

        FindCustomerRequest findCustomerRequest = new FindCustomerRequest(id);
        // when
        final CustomerResponse customer = customerService.findCustomer(findCustomerRequest);
        // then
        assertThat(customer.getUsername()).isEqualTo("some-name");
    }

    @DisplayName("id가 들어있는 요청으로 사용자정보를 수정한다.")
    @Test
    void updateCustomer() {
        // given
        FindCustomerRequest findRequest = new FindCustomerRequest(id);
        UpdateCustomerRequest updateRequest = new UpdateCustomerRequest("other-address", "010-4321-4321");
        // when

        customerService.updateCustomer(findRequest, updateRequest);

        // then
        final CustomerResponse customer = customerService.findCustomer(findRequest);
        assertThat(customer.getAddress()).isEqualTo("other-address");
    }

    @DisplayName("사용자를 삭제한다.")
    @Test
    void deleteCustomer() {
        // given
        FindCustomerRequest findRequest = new FindCustomerRequest(id);
        // when
        customerService.deleteCustomer(findRequest);
        // then

        assertThatExceptionOfType(CustomerNotFoundException.class)
            .isThrownBy(() -> customerService.findCustomer(findRequest));
    }

    @DisplayName("이름이 이미 존재하는지 확인한다.")
    @Test
    void isDuplicatedName() {
        // given
        final String duplicatedName = "some-name";
        // when
        final UserNameDuplicationResponse duplicationResponse = customerService.isUserNameDuplicated(duplicatedName);
        // then
        assertThat(duplicationResponse.getDuplicated()).isTrue();
    }

    @DisplayName("이메일이 이미 존재하는지 확인한다.")
    @Test
    void isDuplicatedEmail() {
        // given
        final String duplicatedEmail = "example@email.com";
        // when
        final EmailDuplicationResponse duplicationResponse = customerService.isEmailDuplicated(duplicatedEmail);
        // then
        assertThat(duplicationResponse.getDuplicated()).isTrue();
    }
}