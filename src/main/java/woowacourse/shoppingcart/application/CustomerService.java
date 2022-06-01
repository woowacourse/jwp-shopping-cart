package woowacourse.shoppingcart.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.DeleteCustomerRequest;
import woowacourse.auth.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;
import woowacourse.shoppingcart.exception.DuplicatedAccountException;
import woowacourse.shoppingcart.exception.WrongPasswordException;

import java.util.Locale;

@Service
@Transactional
public class CustomerService {

    private static final String EXCLUDE_NUMBER_AND_ALPHABET = "[^\\da-zA-Z]";

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerDao customerDao, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerResponse create(SignupRequest signupRequest) {
        final Customer customer = toCustomer(signupRequest);

        if (customerDao.findByAccount(customer.getAccount()).isPresent()) {
            throw new DuplicatedAccountException();
        }
        final Customer savedCustomer = customerDao.save(customer);
        return CustomerResponse.of(savedCustomer);
    }

    private Customer toCustomer(SignupRequest signupRequest) {
        final String processedAccount = signupRequest.getAccount().replaceAll(EXCLUDE_NUMBER_AND_ALPHABET, "").toLowerCase(Locale.ROOT).trim();
        return new Customer(processedAccount,
                signupRequest.getNickname(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getAddress(),
                signupRequest.getPhoneNumber().appendNumbers());
    }

    @Transactional(readOnly = true)
    public CustomerResponse getById(long customerId) {
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        return CustomerResponse.of(customer);
    }

    public int update(long customerId, UpdateCustomerRequest updateCustomerRequest) {
        return customerDao.update(
                customerId,
                updateCustomerRequest.getNickname(),
                updateCustomerRequest.getAddress(),
                updateCustomerRequest.getPhoneNumber().appendNumbers());
    }

    public int delete(long id, DeleteCustomerRequest deleteCustomerRequest) {
        final Customer customer = customerDao.findById(id).orElseThrow(CustomerNotFoundException::new);
        if (!passwordEncoder.matches(deleteCustomerRequest.getPassword(), customer.getPassword())) {
            throw new WrongPasswordException();
        }
        return customerDao.deleteById(id);
    }
}
