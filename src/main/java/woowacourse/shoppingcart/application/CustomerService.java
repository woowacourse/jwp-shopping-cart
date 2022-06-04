package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.EmailRequest;
import woowacourse.shoppingcart.dto.EmailResponse;
import woowacourse.auth.support.JwtTokenProvider;
import woowacourse.auth.utils.Encryptor;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.InvalidTokenException;
import woowacourse.shoppingcart.utils.CustomerInformationValidator;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    private final JwtTokenProvider jwtTokenProvider;

    public CustomerService(CustomerDao customerDao, JwtTokenProvider jwtTokenProvider) {
        this.customerDao = customerDao;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public CustomerResponse register(CustomerRequest customerRequest) {
        CustomerInformationValidator.validatePassword(customerRequest.getPassword());
        final Customer customer = new Customer(customerRequest.getEmail(), customerRequest.getName(), customerRequest.getPhone(), customerRequest.getAddress(), Encryptor.encrypt(customerRequest.getPassword()));
        final Customer savedCustomer = customerDao.save(customer);
        return new CustomerResponse(savedCustomer.getId(), savedCustomer.getEmail(), savedCustomer.getName(), savedCustomer.getPhone(), savedCustomer.getAddress());
    }

    public CustomerResponse findCustomerByToken(String token) {
        validateToken(token);
        final Long id = Long.parseLong(jwtTokenProvider.getPayload(token));
        final Customer customer = customerDao.findById(id);
        return new CustomerResponse(customer.getId(), customer.getEmail(), customer.getName(), customer.getPhone(), customer.getAddress());
    }

    public EmailResponse isDuplication(EmailRequest emailRequest) {
        return new EmailResponse(!customerDao.isDuplication(emailRequest.getEmail()));
    }

    public void edit(String token, CustomerRequest customerRequest) {
        validateToken(token);
        final Long id = Long.parseLong(jwtTokenProvider.getPayload(token));
        CustomerInformationValidator.validatePassword(customerRequest.getPassword());
        final Customer customer = new Customer(id, customerRequest.getEmail(), customerRequest.getName(), customerRequest.getPhone(), customerRequest.getAddress(), Encryptor.encrypt(customerRequest.getPassword()));
        customerDao.save(customer);
    }

    public void delete(String token) {
        validateToken(token);
        final Long id = Long.parseLong(jwtTokenProvider.getPayload(token));
        customerDao.delete(id);
    }

    private void validateToken(String token) {
        if (!jwtTokenProvider.validateToken(token)) {
            throw new InvalidTokenException();
        }
    }
}
