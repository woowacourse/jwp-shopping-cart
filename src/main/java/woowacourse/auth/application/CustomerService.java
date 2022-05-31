package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerUpdateRequest;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.exception.InvalidCustomerException;

@RequiredArgsConstructor
@Transactional
@Service
public class CustomerService {

	private final CustomerDao customerDao;

	public Customer signUp(CustomerRequest request) {
		Customer customer = request.toEntity();
		validateEmailDuplicated(customer);
		return customerDao.save(customer);
	}

	private void validateEmailDuplicated(Customer customer) {
		if (customerDao.existByEmail(customer.getEmail())) {
			throw new InvalidCustomerException("중복된 이메일 입니다.");
		}
	}

	@Transactional(readOnly = true)
	public Customer findByEmail(String email) {
		return customerDao.findByEmail(email)
			.orElseThrow(() -> new InvalidCustomerException("이메일에 해당하는 회원이 존재하지 않습니다"));
	}

	public void delete(Customer customer) {
		customerDao.delete(customer.getId());
	}

	public Customer update(Customer customer, CustomerUpdateRequest request) {
		validatePassword(customer, request);
		Customer updatedCustomer = new Customer(customer.getId(),
			customer.getEmail(),
			request.getNewPassword(),
			request.getNickname()
		);
		customerDao.update(updatedCustomer);
		return updatedCustomer;
	}

	private void validatePassword(Customer customer, CustomerUpdateRequest request) {
		if (customer.isInvalidPassword(request.getPassword())) {
			throw new InvalidAuthException("비밀번호가 달라서 수정할 수 없습니다.");
		}
	}
}

