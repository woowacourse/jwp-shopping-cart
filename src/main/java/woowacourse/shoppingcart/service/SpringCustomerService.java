package woowacourse.shoppingcart.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.badrequest.EmailDuplicateException;
import woowacourse.exception.notfound.CustomerNotFoundException;
import woowacourse.exception.unauthorized.PasswordIncorrectException;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.infra.CustomerRepository;
import woowacourse.shoppingcart.service.dto.CustomerCreateServiceRequest;
import woowacourse.shoppingcart.service.dto.CustomerDeleteServiceRequest;
import woowacourse.shoppingcart.service.dto.CustomerUpdatePasswordServiceRequest;
import woowacourse.shoppingcart.service.dto.CustomerUpdateProfileServiceRequest;

@Transactional(readOnly = true)
@Service
public class SpringCustomerService implements CustomerService {
    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;

    public SpringCustomerService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    @Override
    public Customer create(CustomerCreateServiceRequest customerCreateServiceRequest) {
        validateDuplicateEmail(customerCreateServiceRequest);

        final String hashedPassword = passwordEncoder.encode(customerCreateServiceRequest.getPassword());
        final Customer customer = new Customer(customerCreateServiceRequest.getEmail(),
                customerCreateServiceRequest.getName(), hashedPassword);

        return customerRepository.save(customer);
    }

    private void validateDuplicateEmail(CustomerCreateServiceRequest customerCreateServiceRequest) {
        if (customerRepository.findByEmail(customerCreateServiceRequest.getEmail()).isPresent()) {
            throw new EmailDuplicateException();
        }
    }

    @Override
    public Customer getById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public Customer getByEmail(String email) {
        return customerRepository.findByEmail(email)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Override
    public Customer getByName(String name) {
        return customerRepository.findByName(name)
                .orElseThrow(CustomerNotFoundException::new);
    }

    @Transactional
    @Override
    public Customer updateProfile(Long id, CustomerUpdateProfileServiceRequest customerUpdateProfileRequest) {
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        return customerRepository.save(customer.changeName(customerUpdateProfileRequest.getName()));
    }

    @Transactional
    @Override
    public Customer updatePassword(Long id, CustomerUpdatePasswordServiceRequest customerUpdatePasswordRequest) {
        final Customer customer = customerRepository.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        validatePassword(customer, customerUpdatePasswordRequest.getOldPassword());

        return customerRepository.save(customer.changePassword(customerUpdatePasswordRequest.getNewPassword()));
    }

    private void validatePassword(Customer customer, String inputPassword) {
        if (!customer.validatePassword(inputPassword, passwordEncoder)) {
            throw new PasswordIncorrectException();
        }
    }

    @Transactional
    @Override
    public long delete(long id, CustomerDeleteServiceRequest customerDeleteRequest) {
        final Customer customer = customerRepository.findById(id).orElseThrow(CustomerNotFoundException::new);
        validatePassword(customer, customerDeleteRequest.getPassword());

        customerRepository.deleteById(id);
        return id;
    }
}
