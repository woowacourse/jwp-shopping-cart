package woowacourse.auth.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.exception.InvalidMemberException;

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
		CustomerRequest request = new CustomerRequest("123@gmail.com", "!234");
		Customer customer = new Customer(1L, "123@gmail.com", "!234");
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
		CustomerRequest request = new CustomerRequest("123@gmail.com", "!234");
		given(customerDao.existByEmail("123@gmail.com"))
			.willReturn(true);

		// then
		assertThatThrownBy(() -> customerService.signUp(request))
			.isInstanceOf(InvalidMemberException.class);
	}
}
