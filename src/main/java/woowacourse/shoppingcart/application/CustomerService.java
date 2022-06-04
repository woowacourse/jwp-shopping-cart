package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.dto.ValidEmailRequest;
import woowacourse.auth.dto.ValidEmailResponse;
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

    public ValidEmailResponse isValidEmail(ValidEmailRequest validEmailRequest) {
        return new ValidEmailResponse(!customerDao.isDuplicationEmail(validEmailRequest.getEmail()));
    }

    public void edit(String token, CustomerRequest customerRequest) {
        validateToken(token);
        final Long id = Long.parseLong(jwtTokenProvider.getPayload(token));
        CustomerInformationValidator.validatePassword(customerRequest.getPassword());
        final Customer customer = new Customer(id, customerRequest.getEmail(), customerRequest.getName(), customerRequest.getPhone(), customerRequest.getAddress(), Encryptor.encrypt(customerRequest.getPassword()));
        customerDao.edit(customer);
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
