package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.customer.CustomerDeleteRequest;
import woowacourse.auth.domain.EncryptionStrategy;
import woowacourse.auth.domain.Password;
import woowacourse.auth.dto.customer.CustomerProfileRequest;
import woowacourse.auth.dto.customer.CustomerRequest;
import woowacourse.auth.dto.customer.CustomerPasswordRequest;
import woowacourse.exception.ErrorCode;
import woowacourse.exception.InvalidAuthException;
import woowacourse.exception.InvalidCustomerException;

@RequiredArgsConstructor
@Transactional
@Service
public class CustomerService {

	private final CustomerDao customerDao;
	private final EncryptionStrategy encryptionStrategy;

	public Customer signUp(CustomerRequest request) {
		validateEmailDuplicated(request.getEmail());
		return customerDao.save(Customer.builder()
			.email(request.getEmail())
			.nickname(request.getNickname())
			.password(request.getPassword())
			.encryptPassword(encryptionStrategy)
			.build()
		);
	}

	private void validateEmailDuplicated(String email) {
		if (customerDao.existByEmail(email)) {
			throw new InvalidCustomerException(ErrorCode.DUPLICATE_EMAIL, "중복된 이메일 입니다.");
		}
	}

	@Transactional(readOnly = true)
	public Customer findByEmail(String email) {
		return customerDao.findByEmail(email)
			.orElseThrow(() -> new InvalidCustomerException(ErrorCode.LOGIN, "이메일에 해당하는 회원이 존재하지 않습니다"));
	}

	public void delete(Customer customer, CustomerDeleteRequest request) {
		validatePassword(customer, new Password(request.getPassword()));
		customerDao.delete(customer.getId());
	}

	public Customer updatePassword(Customer customer, CustomerPasswordRequest request) {
		validatePassword(customer, new Password(request.getPassword()));
		Customer updatedCustomer = Customer.builder()
			.id(customer.getId())
			.email(customer.getEmail())
			.nickname(customer.getNickname())
			.password(request.getNewPassword())
			.encryptPassword(encryptionStrategy)
			.build();
		customerDao.update(updatedCustomer);
		return updatedCustomer;
	}

	private void validatePassword(Customer customer, Password password) {
		String encrypted = encryptionStrategy.encode(password);
		if (customer.isInvalidPassword(encrypted)) {
			throw new InvalidAuthException(ErrorCode.PASSWORD_NOT_MATCH, "비밀번호가 달라서 수정할 수 없습니다.");
		}
	}

	public Customer updateProfile(Customer customer, CustomerProfileRequest request) {
		Customer updatedCustomer = Customer.builder()
			.id(customer.getId())
			.email(customer.getEmail())
			.nickname(request.getNickname())
			.password(customer.getPassword())
			.build();
		customerDao.update(updatedCustomer);
		return updatedCustomer;
	}
}

