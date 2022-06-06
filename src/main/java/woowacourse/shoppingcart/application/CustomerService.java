package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.password.PasswordEncoder;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerSaveRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.customer.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerDao customerDao,
            PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponse save(CustomerSaveRequest customerSaveRequest) {
        Customer customer = customerSaveRequest.toCustomer(passwordEncoder);
        Customer savedCustomer = customerDao.save(customer);
        return new CustomerResponse(savedCustomer);
    }

    public CustomerResponse find(LoginCustomer loginCustomer) {
        Customer customer = getCustomer(loginCustomer.getUsername());
        return new CustomerResponse(customer);
    }

    public void update(LoginCustomer loginCustomer, CustomerUpdateRequest customerUpdateRequest) {
        Customer customer = getCustomer(loginCustomer.getUsername());
        customer.modify(customerUpdateRequest.getAddress(), customerUpdateRequest.getPhoneNumber());
        customerDao.update(customer);
    }

    public void delete(LoginCustomer loginCustomer) {
        Customer customer = getCustomer(loginCustomer.getUsername());
        customerDao.delete(customer);
    }

    private Customer getCustomer(String username) {
        return customerDao.findByUsername(username)
                .orElseThrow(InvalidCustomerException::new);
    }
}
