package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.domain.Password;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerUpdateRequest;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.exception.InvalidCustomerException;
import woowacourse.auth.support.EncryptionStrategy;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	private final String email = "123@gmail.com";
	private final String password = "a1234!";
	private final String nickname = "does";

	@Mock
	private CustomerDao customerDao;
	@Mock
	private EncryptionStrategy encryptionStrategy;
	@InjectMocks
	private CustomerService customerService;

	@DisplayName("회원 정보를 저장한다.")
	@Test
	void sighUp() {
		// given
		CustomerRequest request = new CustomerRequest(email, password, nickname);
		Customer customer = new Customer(1L, email, password, nickname);
		given(customerDao.save(any(Customer.class)))
			.willReturn(customer);
		given(encryptionStrategy.encode(new Password(password)))
			.willReturn("ASEFASEGAERAETG");

		// when
		Customer saved = customerService.signUp(request);

		// then
		assertAll(
			() -> assertThat(saved).isEqualTo(customer),
			() -> verify(customerDao).save(any(Customer.class))
		);
	}

	@DisplayName("중복 이메일은 저장하지 못 한다.")
	@Test
	void emailDuplicate() {
		// given
		CustomerRequest request = new CustomerRequest(email, password, nickname);
		given(customerDao.existByEmail(email))
			.willReturn(true);

		// then
		assertAll(
			() -> assertThatThrownBy(() -> customerService.signUp(request))
				.isInstanceOf(InvalidCustomerException.class),
			() -> verify(customerDao, never()).save(any())
		);
	}

	@DisplayName("이메일로 회원을 조회한다.")
	@Test
	void findByEmail() {
		// given
		Customer customer = new Customer(1L, email, password, nickname);
		given(customerDao.findByEmail(email))
			.willReturn(Optional.of(customer));

		// then
		assertAll(
			() -> assertThat(customerService.findByEmail(email)).isEqualTo(customer),
			() -> verify(customerDao).findByEmail(email)
		);
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
		CustomerUpdateRequest request = new CustomerUpdateRequest(
			"thor", password, "b1234!");
		Customer customer = new Customer(1L, email, password, nickname);
		given(encryptionStrategy.encode(new Password(password)))
			.willReturn(password);

		// when
		Customer update = customerService.update(customer, request);

		// then
		assertAll(
			() -> assertThat(update.getNickname()).isEqualTo("thor"),
			() -> verify(customerDao).update(customer)
		);
	}

	@DisplayName("기존 비밀번호가 다르면 수정하지 못한다.")
	@Test
	void updateCustomerPasswordFail() {
		// given
		CustomerUpdateRequest request = new CustomerUpdateRequest(
			"thor", "a1234!", "b1234!");
		Customer customer = new Customer(1L, email, "a123456!", "b1234!");

		// when
		assertAll(
			() -> assertThatThrownBy(() -> customerService.update(customer, request))
				.isInstanceOf(InvalidAuthException.class),
			() -> verify(customerDao, never()).update(any())
		);
	}
}
