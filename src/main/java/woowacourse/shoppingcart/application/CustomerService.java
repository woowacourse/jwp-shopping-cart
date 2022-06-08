package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.password.PasswordEncoder;
import woowacourse.shoppingcart.dto.customer.LoginCustomer;
import woowacourse.shoppingcart.dto.customer.request.CustomerSaveRequest;
import woowacourse.shoppingcart.dto.customer.request.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.customer.request.EmailDuplicateRequest;
import woowacourse.shoppingcart.dto.customer.request.UsernameDuplicateRequest;
import woowacourse.shoppingcart.dto.customer.response.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.response.EmailDuplicateResponse;
import woowacourse.shoppingcart.dto.customer.response.UsernameDuplicateResponse;
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

    private Customer getCustomer(String username) {
        return customerDao.findByUsername(username)
                .orElseThrow(InvalidCustomerException::new);
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

    public UsernameDuplicateResponse checkUsernameDuplicate(UsernameDuplicateRequest usernameDuplicateRequest) {
        String username = usernameDuplicateRequest.getUsername();
        ;
        if (customerDao.findByUsername(username).isPresent()) {
            return new UsernameDuplicateResponse(username, true);
        }
        return new UsernameDuplicateResponse(username, false);
    }

    public EmailDuplicateResponse checkEmailDuplicate(EmailDuplicateRequest emailDuplicateRequest) {
        String email = emailDuplicateRequest.getEmail();
        if (customerDao.findEmailByEmail(email).isPresent()) {
            return new EmailDuplicateResponse(email, true);
        }
        return new EmailDuplicateResponse(email, false);
    }
}
