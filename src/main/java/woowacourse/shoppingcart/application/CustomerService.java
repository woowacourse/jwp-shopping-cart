package woowacourse.shoppingcart.application;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.exception.EmptyResultException;

@Service
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer save(SignupRequest signupRequest) {
        Customer customer = Customer.of(
            signupRequest.getUsername(),
            signupRequest.getPassword(),
            signupRequest.getPhoneNumber(),
            signupRequest.getAddress()
        );

        return customerDao.save(customer);
    }

    public Customer findByUsername(String username) {
        return customerDao.findByUsername(username)
            .orElseThrow(throwEmptyCustomerException());
    }

    private Supplier<EmptyResultException> throwEmptyCustomerException() {
        return () -> new EmptyResultException("해당 username으로 customer를 찾을 수 없습니다.");
    }
}
