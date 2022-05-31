package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import woowacourse.auth.application.CustomerService;
import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.exception.InvalidCustomerException;

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
		CustomerRequest request = new CustomerRequest("123@gmail.com", "!234", "does");
		Customer customer = new Customer(1L, "123@gmail.com", "!234", "does");
		given(customerDao.save(any(Customer.class)))
			.willReturn(customer);

		// when
		Customer saved = customerService.signUp(request);

		// then
		assertThat(saved).isEqualTo(customer);
	}

	@DisplayName("중복 이메일은 저장하지 못 한다.")
	@Test
	void emailDuplicate() {
		// given
		CustomerRequest request = new CustomerRequest("123@gmail.com", "!234", "does");
		given(customerDao.existByEmail("123@gmail.com"))
			.willReturn(true);

		// then
		assertThatThrownBy(() -> customerService.signUp(request))
			.isInstanceOf(InvalidCustomerException.class);
	}

	@DisplayName("이메일로 회원을 조회한다.")
	@Test
	void findByEmail() {
		// given
		Customer customer = new Customer(1L, "123@gmail.com", "!234", "does");
		given(customerDao.findByEmail("123@gmail.com"))
			.willReturn(Optional.of(customer));

		// then
		assertThat(customerService.findByEmail("123@gmail.com")).isEqualTo(customer);
	}

	@DisplayName("이메일로 회원을 조회한다.")
	@Test
	void findByEmail_failByCustomer() {
		// given
		given(customerDao.findByEmail("123@gmail.com"))
			.willReturn(Optional.empty());

		// then
		assertThatThrownBy(() ->customerService.findByEmail("123@gmail.com"))
			.isInstanceOf(InvalidCustomerException.class);
	}
}
