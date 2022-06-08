package woowacourse.shoppingcart.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static woowacourse.utils.Fixture.customer;
import static woowacourse.utils.Fixture.email;
import static woowacourse.utils.Fixture.password;
import static woowacourse.utils.Fixture.signupRequest;

import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import woowacourse.auth.dto.customer.CustomerUpdateRequest;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.exception.InvalidCustomerException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;
import woowacourse.shoppingcart.exception.IncorrectPasswordException;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;
    @InjectMocks
    private CustomerService customerService;

    @DisplayName("회원 정보를 저장한다.")
    @Test
    void sighUp() {
        // given
        given(customerDao.save(any(Customer.class)))
                .willReturn(customer);

        // when
        Customer saved = customerService.signUp(signupRequest);

        // then
        assertThat(saved).isEqualTo(customer);
    }

    @DisplayName("중복 이메일은 저장하지 못 한다.")
    @Test
    void emailDuplicate() {
        // given
        given(customerDao.existByEmail(email))
                .willReturn(true);

        // then
        assertThatThrownBy(() -> customerService.signUp(signupRequest))
                .isInstanceOf(DuplicatedEmailException.class);
    }

    @DisplayName("이메일로 회원을 조회한다.")
    @Test
    void findByEmail() {
        // given
        given(customerDao.findByEmail(email))
                .willReturn(Optional.of(customer));

        // then
        assertThat(customerService.findByEmail(email)).isEqualTo(customer);
    }

    @DisplayName("이메일로 회원을 조회한다.")
    @Test
    void findByEmail_failByCustomer() {
        // given
        given(customerDao.findByEmail(email))
                .willReturn(Optional.empty());

        // then
        assertThatThrownBy(() -> customerService.findByEmail(email))
                .isInstanceOf(InvalidCustomerException.class);
    }

    @DisplayName("회원 정보를 수정한다.")
    @Test
    void updateCustomer() {
        // given
        CustomerUpdateRequest request = new CustomerUpdateRequest("thor", password, "b1234!");
        given(customerDao.findByEmail(any(String.class)))
                .willReturn(Optional.of(customer));

        // when
        Customer update = customerService.update(email, request);

        // then
        assertThat(update.getNickname()).isEqualTo("thor");
    }

    @DisplayName("기존 비밀번호가 다르면 수정하지 못한다.")
    @Test
    void updateCustomerPasswordFail() {
        // given
        CustomerUpdateRequest request = new CustomerUpdateRequest("thor", "differ!1", "b1234!");
        given(customerDao.findByEmail(any(String.class)))
                .willReturn(Optional.of(customer));
        // when
        assertThatThrownBy(() -> customerService.update("other@gmail.com", request))
                .isInstanceOf(IncorrectPasswordException.class);
    }
}
