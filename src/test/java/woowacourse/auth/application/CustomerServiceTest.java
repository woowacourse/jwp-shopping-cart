package woowacourse.auth.application;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.domain.Password;
import woowacourse.auth.dto.customer.CustomerDeleteRequest;
import woowacourse.auth.dto.customer.CustomerRequest;
import woowacourse.auth.dto.customer.CustomerPasswordRequest;
import woowacourse.exception.InvalidAuthException;
import woowacourse.exception.InvalidCustomerException;
import woowacourse.auth.domain.EncryptionStrategy;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

	private final String email = "123@gmail.com";
	private final String password = "a1234!";
	private final String nickname = "does";

	@Mock
	private CustomerDao customerDao;
	private EncryptionStrategy encryptionStrategy;
	private CustomerService customerService;

	@BeforeEach
	void init() {
		encryptionStrategy = Password::getValue;
		customerService = new CustomerService(customerDao, Password::getValue);
	}

	@DisplayName("회원 정보를 저장한다.")
	@Test
	void sighUp() {
		// given
		CustomerRequest request = new CustomerRequest(email, password, nickname);
		Customer customer = Customer.builder()
			.id(1L)
			.email(email)
			.nickname(nickname)
			.password(password)
			.encryptPassword(encryptionStrategy)
			.build();
		given(customerDao.save(any(Customer.class)))
			.willReturn(customer);

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
		Customer customer = Customer.builder()
			.id(1L)
			.email(email)
			.nickname(nickname)
			.password(password)
			.encryptPassword(encryptionStrategy)
			.build();
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

	@DisplayName("회원 비밀번호를 수정한다.")
	@Test
	void updateCustomer() {
		// given
		CustomerPasswordRequest request = new CustomerPasswordRequest(password, "b1234!");
		Customer customer = Customer.builder()
			.id(1L)
			.email(email)
			.nickname(nickname)
			.password(password)
			.encryptPassword(encryptionStrategy)
			.build();

		// when
		Customer update = customerService.updatePassword(customer, request);

		// then
		assertAll(
			() -> assertThat(update.getNickname()).isEqualTo(nickname),
			() -> assertThat(update.isInvalidPassword(customer.getPassword())).isTrue(),
			() -> verify(customerDao).update(customer)
		);
	}

	@DisplayName("기존 비밀번호가 다르면 수정하지 못한다.")
	@Test
	void updateCustomerPasswordFail() {
		// given
		CustomerPasswordRequest request = new CustomerPasswordRequest("a1234!", "b1234!");
		Customer customer = Customer.builder()
			.id(1L)
			.email(email)
			.nickname("nickname")
			.password("a123456!")
			.encryptPassword(encryptionStrategy)
			.build();

		// when
		assertAll(
			() -> assertThatThrownBy(() -> customerService.updatePassword(customer, request))
				.isInstanceOf(InvalidAuthException.class),
			() -> verify(customerDao, never()).update(any())
		);
	}

	@DisplayName("회원 탈퇴를 진행한다.")
	@Test
	void signOutSuccess() {
		// given
		Customer customer = Customer.builder()
			.id(1L)
			.email(email)
			.nickname(nickname)
			.password(password)
			.encryptPassword(encryptionStrategy)
			.build();
		CustomerDeleteRequest request = new CustomerDeleteRequest(password);

		// when
		customerService.delete(customer, request);

		// then
		verify(customerDao).delete(1L);
	}

	@DisplayName("비밀번호가 다르면 회원 탈퇴를 못한다.")
	@Test
	void signOutFail() {
		// given
		String misMatchPassword = "b1234!";
		Customer customer = Customer.builder()
			.id(1L)
			.email(email)
			.nickname(nickname)
			.password(password)
			.encryptPassword(encryptionStrategy)
			.build();
		CustomerDeleteRequest request = new CustomerDeleteRequest(misMatchPassword);

		// when
		assertThatThrownBy(() -> customerService.delete(customer, request))
			.isInstanceOf(InvalidAuthException.class);

		// then
		verify(customerDao, never()).delete(1L);
	}
}
