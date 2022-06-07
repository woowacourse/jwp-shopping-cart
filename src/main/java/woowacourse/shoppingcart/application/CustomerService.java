package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.*;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.EmailRequest;
import woowacourse.shoppingcart.dto.customer.EmailResponse;
import woowacourse.shoppingcart.exception.InvalidTokenException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    private final JwtTokenProvider jwtTokenProvider;

    public CustomerService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public CustomerResponse register(CustomerRequest customerRequest) {
        final Customer customer = new Customer(new Email(customerRequest.getEmail()), new Name(customerRequest.getName()), new Phone(customerRequest.getPhone()), new Address(customerRequest.getAddress()), Password.of(customerRequest.getPassword()));
        final Customer savedCustomer = customerDao.save(customer);
        return new CustomerResponse(savedCustomer.getId().getValue(), savedCustomer.getEmail().getValue(), savedCustomer.getName().getValue(), savedCustomer.getPhone().getValue(), savedCustomer.getAddress().getValue());
    }

    public CustomerResponse findCustomerByToken(String token) {
        validateToken(token);
        final CustomerId customerId = new CustomerId(getCustomerId(token));
        final Customer customer = customerDao.findById(customerId);
        return new CustomerResponse(customer.getId().getValue(), customer.getEmail().getValue(), customer.getName().getValue(), customer.getPhone().getValue(), customer.getAddress().getValue());
    }

    public EmailResponse checkValidEmail(EmailRequest emailRequest) {
        return new EmailResponse(!customerDao.isDuplication(new Email(emailRequest.getEmail())));
    }

    public void edit(String token, CustomerRequest customerRequest) {
        validateToken(token);
        final CustomerId customerId = new CustomerId(getCustomerId(token));
        final Customer customer = new Customer(customerId, new Email(customerRequest.getEmail()), new Name(customerRequest.getName()), new Phone(customerRequest.getPhone()), new Address(customerRequest.getAddress()), Password.of(customerRequest.getPassword()));
        customerDao.save(customer);
    }

    public void delete(String token) {
        validateToken(token);
        final CustomerId customerId = new CustomerId(getCustomerId(token));
        customerDao.delete(customerId);
    }

    public void validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }

    public Long getCustomerId(String token) {
        return Long.parseLong(jwtTokenProvider.getPayload(token));
    }
}
