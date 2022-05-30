package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.SignupRequest;

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
}
