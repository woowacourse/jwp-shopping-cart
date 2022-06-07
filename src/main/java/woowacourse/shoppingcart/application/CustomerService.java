package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.EmailDuplicateException;
import woowacourse.exception.PasswordIncorrectException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Encoder;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;
import woowacourse.shoppingcart.ui.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerRequest;
import woowacourse.shoppingcart.ui.dto.response.CustomerResponse;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.ui.dto.request.CustomerUpdateProfileRequest;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final Encoder encoder;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
        this.encoder = new PasswordEncoderAdapter();
    }

    @Transactional
    public Long create(CustomerRequest customerRequest) {
        if (customerDao.findByEmail(customerRequest.getEmail()).isPresent()) {
            throw new EmailDuplicateException();
        }

        final String hashPassword = encoder.encode(customerRequest.getPassword());
        final Customer customer = new Customer(
                new Email(customerRequest.getEmail()),
                customerRequest.getName(),
                new Password(hashPassword));

        return customerDao.save(customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long id) {
        final Customer customer = customerDao.findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        return new CustomerResponse(customer.getName(), customer.getEmail());
    }

    @Transactional
    public Long updateProfile(Long id, CustomerUpdateProfileRequest customerUpdateProfileRequest) {
        final Customer customer = customerDao.findById(id)
                .orElseThrow(CustomerNotFoundException::new);

        final Customer newCustomer = customer.changeName(customerUpdateProfileRequest.getName());
        customerDao.updateProfile(newCustomer);
        return id;
    }

    @Transactional
    public Long updatePassword(Long id, CustomerUpdatePasswordRequest customerUpdatePasswordRequest) {
        final Customer customer = customerDao.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
        validatePassword(customer, customerUpdatePasswordRequest.getOldPassword());

        final String hashPassword = encoder.encode(customerUpdatePasswordRequest.getNewPassword());
        final Password newPassword = new Password(hashPassword);
        final Customer newCustomer = customer.changePassword(newPassword);
        customerDao.updatePassword(newCustomer);
        return id;
    }

    public Long delete(long id, CustomerDeleteRequest customerDeleteRequest) {
        final Customer customer = customerDao.findById(id).orElseThrow(CustomerNotFoundException::new);
        validatePassword(customer, customerDeleteRequest.getPassword());

        customerDao.delete(id);
        return id;
    }

    private void validatePassword(Customer customer, String inputPassword) {
        if (!customer.validatePassword(new Password(inputPassword), encoder)) {
            throw new PasswordIncorrectException();
        }
    }
}
