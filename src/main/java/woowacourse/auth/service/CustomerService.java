package woowacourse.auth.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import woowacourse.auth.dao.CustomerDao;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.CustomerRequest;
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

	public Customer findByEmail(String email) {
		return customerDao.findByEmail(email)
			.orElseThrow(() -> new InvalidCustomerException("이메일에 해당하는 회원이 존재하지 않습니다"));
	}
}

