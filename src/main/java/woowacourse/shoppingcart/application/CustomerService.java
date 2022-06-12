package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.exception.EmailDuplicateException;
import woowacourse.exception.PasswordIncorrectException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.dto.request.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.request.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.request.CustomerUpdateProfileRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.exception.CustomerNotFoundException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public Long create(CustomerRequest customerRequest) {
        if (customerDao.findByEmail(customerRequest.getEmail()).isPresent()) {
            throw new EmailDuplicateException();
        }

        final Customer customer = new Customer(
                new Email(customerRequest.getEmail()),
                customerRequest.getName(),
                Password.planePassword(customerRequest.getPassword())
        );

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
        validatePasswordIsCorrect(customer, customerUpdatePasswordRequest.getOldPassword());

        final Password newPassword = Password.planePassword(customerUpdatePasswordRequest.getNewPassword());
        final Customer newCustomer = customer.changePassword(newPassword);
        customerDao.updatePassword(newCustomer);
        return id;
    }

    @Transactional
    public Long delete(long id, CustomerDeleteRequest customerDeleteRequest) {
        final Customer customer = customerDao.findById(id).orElseThrow(CustomerNotFoundException::new);
        validatePasswordIsCorrect(customer, customerDeleteRequest.getPassword());

        customerDao.delete(id);
        return id;
    }

    private void validatePasswordIsCorrect(Customer customer, String password) {
        if (!customer.isCorrectPassword(password)) {
            throw new PasswordIncorrectException();
        }
    }
}
