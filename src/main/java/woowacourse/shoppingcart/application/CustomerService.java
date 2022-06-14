package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.*;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.EmailRequest;
import woowacourse.shoppingcart.dto.customer.EmailResponse;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    private final JwtTokenProvider jwtTokenProvider;

    public CustomerService(final CustomerDao customerDao, final JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public CustomerResponse register(final CustomerRequest customerRequest) {
        final Customer customer = new Customer(new Email(customerRequest.getEmail()), new Name(customerRequest.getName()), new Phone(customerRequest.getPhone()), new Address(customerRequest.getAddress()), Password.of(customerRequest.getPassword()));
        final Customer savedCustomer = customerDao.save(customer);
        return new CustomerResponse(savedCustomer.getId().getValue(), savedCustomer.getEmail().getValue(), savedCustomer.getName().getValue(), savedCustomer.getPhone().getValue(), savedCustomer.getAddress().getValue());
    }

    public CustomerResponse findCustomerBy(final Long id) {
        final Customer customer = customerDao.findById(new CustomerId(id));
        return new CustomerResponse(customer.getId().getValue(), customer.getEmail().getValue(), customer.getName().getValue(), customer.getPhone().getValue(), customer.getAddress().getValue());
    }

    public EmailResponse checkValidEmail(final EmailRequest emailRequest) {
        return new EmailResponse(!customerDao.isDuplication(new Email(emailRequest.getEmail())));
    }

    public void edit(final Long id, final CustomerRequest customerRequest) {
        final Customer customer = new Customer(new CustomerId(id), new Email(customerRequest.getEmail()), new Name(customerRequest.getName()), new Phone(customerRequest.getPhone()), new Address(customerRequest.getAddress()), Password.of(customerRequest.getPassword()));
        customerDao.save(customer);
    }

    public void delete(final Long id) {
        customerDao.delete(new CustomerId(id));
    }
}
