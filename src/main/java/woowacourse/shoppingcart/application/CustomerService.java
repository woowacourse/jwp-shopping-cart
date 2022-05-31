package woowacourse.shoppingcart.application;

import java.util.function.Supplier;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.exception.EmptyResultException;

@Transactional
@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer save(final SignupRequest signupRequest) {
        final Customer customer = Customer.of(
            signupRequest.getUsername(),
            signupRequest.getPassword(),
            signupRequest.getPhoneNumber(),
            signupRequest.getAddress()
        );

        return customerDao.save(customer);
    }

    public Customer findByUsername(final String username) {
        return customerDao.findByUsername(username)
            .orElseThrow(throwEmptyCustomerException());
    }

    private Supplier<EmptyResultException> throwEmptyCustomerException() {
        return () -> new EmptyResultException("해당 username으로 customer를 찾을 수 없습니다.");
    }

    public void updateInfo(final String username, final UpdateCustomerRequest updateCustomerRequest) {
        final Customer customer = findByUsername(username);
        customer.updatePhoneNumber(updateCustomerRequest.getPhoneNumber());
        customer.updateAddress(updateCustomerRequest.getAddress());
        customerDao.update(customer);
    }

    public void updatePassword(final String username, final UpdateCustomerRequest updateCustomerRequest) {
        final Customer customer = findByUsername(username);
        customer.updatePassword(updateCustomerRequest.getPassword());
        customerDao.update(customer);
    }

    public void deleteByUsername(final String username) {
        customerDao.deleteByUsername(username);
    }
}
