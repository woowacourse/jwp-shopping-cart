package woowacourse.shoppingcart.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerDto;
import woowacourse.shoppingcart.dto.DeleteCustomerDto;
import woowacourse.shoppingcart.dto.SignUpDto;
import woowacourse.shoppingcart.dto.UpdateCustomerDto;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.PasswordMismatchException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(final CustomerDao customerDao,
                           final PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    public Long signUp(final SignUpDto signUpDto) {
        customerDao.findByEmail(signUpDto.getEmail())
                .ifPresent(customer -> {throw new DuplicateCustomerException("이미 가입된 이메일입니다.");});
        final Customer newCustomer = Customer.createWithoutId(
                signUpDto.getEmail(),
                passwordEncoder.encrypt(signUpDto.getPassword()),
                signUpDto.getUsername()
        );

        return customerDao.save(newCustomer);
    }

    public CustomerDto findCustomerById(final Long id) {
        final Customer customer = customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
        return new CustomerDto(customer.getId(), customer.getEmail(), customer.getUsername());
    }

    public CustomerDto updateCustomer(final Long id, final UpdateCustomerDto updateCustomerDto) {
        final Customer updateCustomer = Customer.createWithoutEmailAndPassword(
                id,
                updateCustomerDto.getUsername()
        );
        try {
            customerDao.update(updateCustomer);
            return findCustomerById(updateCustomer.getId());
        } catch (final DataIntegrityViolationException e) {
            throw new DuplicateCustomerException("수정하려는 이름이 이미 존재합니다.");
        }
    }

    public void deleteCustomer(final Long id, final DeleteCustomerDto deleteCustomerDto) {
        final Customer customer = customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
        checkPassword(deleteCustomerDto, customer);

        customerDao.deleteById(id);
    }

    private void checkPassword(final DeleteCustomerDto deleteCustomerDto, final Customer customer) {
        if (!passwordEncoder.matches(deleteCustomerDto.getPassword(), customer.getPassword())) {
            throw new PasswordMismatchException();
        }
    }

    public CustomerDto findCustomerByEmail(final String email) {
        final Customer customer = customerDao.findByEmail(email)
                .orElseThrow(InvalidCustomerException::new);
        return new CustomerDto(customer.getId(), customer.getEmail(), customer.getUsername());
    }
}
