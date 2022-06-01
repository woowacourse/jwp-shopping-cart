package woowacourse.shoppingcart.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.DeleteCustomerRequest;
import woowacourse.auth.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;
import woowacourse.shoppingcart.exception.DuplicatedAccountException;
import woowacourse.shoppingcart.exception.WrongPasswordException;

import java.util.Locale;

@Service
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerDao customerDao, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    public CustomerDto create(SignupRequest signupRequest) {
        final Customer customer = toCustomer(signupRequest);

        if (customerDao.findByAccount(customer.getAccount()).isPresent()) {
            throw new DuplicatedAccountException();
        }
        final Customer savedCustomer = customerDao.save(customer);
        return CustomerDto.of(savedCustomer);
    }

    private Customer toCustomer(SignupRequest signupRequest) {
        final String match = "[^\\da-zA-Z]";
        final String processedAccount = signupRequest.getAccount().replaceAll(match, "").toLowerCase(Locale.ROOT).trim();
        return new Customer(processedAccount,
                signupRequest.getNickname(),
                passwordEncoder.encode(signupRequest.getPassword()),
                signupRequest.getAddress(),
                signupRequest.getPhoneNumber().appendNumbers());
    }

    @Transactional(readOnly = true)
    public CustomerDto getById(long customerId) {
        Customer customer = customerDao.findById(customerId)
                .orElseThrow(CustomerNotFoundException::new);

        return CustomerDto.of(customer);
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
