package woowacourse.shoppingcart.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.DeleteCustomerRequest;
import woowacourse.auth.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Account;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.EncodedPassword;
import woowacourse.shoppingcart.domain.PlainPassword;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;
import woowacourse.shoppingcart.exception.DuplicatedAccountException;
import woowacourse.shoppingcart.exception.WrongPasswordException;

@Service
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(final CustomerDao customerDao, final PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponse create(final SignupRequest signupRequest) {
        final Customer customer = toCustomer(signupRequest);
        validateAccountDuplicated(customer);

        return CustomerResponse.of(customerDao.save(customer));
    }

    private void validateAccountDuplicated(final Customer customer) {
        if (customerDao.findByAccount(customer.getAccount().getValue()).isPresent()) {
            throw new DuplicatedAccountException();
        }
    }

    private Customer toCustomer(final SignupRequest signupRequest) {
        final PlainPassword plainPassword = new PlainPassword(signupRequest.getPassword());
        final String encode = plainPassword.encode(passwordEncoder);
        return new Customer(new Account(signupRequest.getAccount()),
                signupRequest.getNickname(),
                new EncodedPassword(encode),
                signupRequest.getAddress(),
                signupRequest.getPhoneNumber().appendNumbers());
    }

    @Transactional(readOnly = true)
    public CustomerResponse getById(final long customerId) {
        final Customer customer = customerDao.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        return CustomerResponse.of(customer);
    }

    public int update(final long customerId, final UpdateCustomerRequest updateCustomerRequest) {
        return customerDao.update(
                customerId,
                updateCustomerRequest.getNickname(),
                updateCustomerRequest.getAddress(),
                updateCustomerRequest.getPhoneNumber().appendNumbers());
    }

    public int delete(final long id, final DeleteCustomerRequest deleteCustomerRequest) {
        final Customer customer = customerDao.findById(id).orElseThrow(CustomerNotFoundException::new);
        validatePasswordMatch(deleteCustomerRequest, customer);

        return customerDao.deleteById(id);
    }

    private void validatePasswordMatch(final DeleteCustomerRequest deleteCustomerRequest, final Customer customer) {
        if (customer.getPassword().isNotMatch(passwordEncoder, deleteCustomerRequest.getPassword())) {
            throw new WrongPasswordException();
        }
    }
}
