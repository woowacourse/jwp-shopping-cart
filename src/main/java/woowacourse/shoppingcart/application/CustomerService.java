package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.exception.NoSuchEmailException;
import woowacourse.shoppingcart.exception.PasswordNotMatchException;
import woowacourse.shoppingcart.application.dto.CustomerDeleteServiceRequest;
import woowacourse.shoppingcart.application.dto.CustomerDetailServiceResponse;
import woowacourse.shoppingcart.application.dto.CustomerPasswordUpdateServiceRequest;
import woowacourse.shoppingcart.application.dto.CustomerProfileUpdateServiceRequest;
import woowacourse.shoppingcart.application.dto.CustomerSaveServiceRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.PasswordConvertor;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordConvertor passwordConvertor;

    public CustomerService(final CustomerDao customerDao, final PasswordConvertor passwordConvertor) {
        this.customerDao = customerDao;
        this.passwordConvertor = passwordConvertor;
    }

    public long validateCustomer(final String email, final String password) {
        final Customer customer = customerDao.findByEmail(email)
                .orElseThrow(NoSuchEmailException::new);
        validatePassword(customer, password);
        return customer.getId();
    }

    private void validatePassword(final Customer customer, final String password) {
        if (customer.unMatchPasswordWith(password, passwordConvertor)) {
            throw new PasswordNotMatchException();
        }
    }

    public void save(final CustomerSaveServiceRequest customerSaveServiceRequest) {
        final Customer customer = new Customer(
                customerSaveServiceRequest.getName(),
                customerSaveServiceRequest.getEmail(),
                Password.fromRawValue(customerSaveServiceRequest.getPassword(), passwordConvertor)
        );

        validateDuplicatedEmail(customer);
        customerDao.save(customer);
    }

    private void validateDuplicatedEmail(final Customer customer) {
        if (customerDao.existsByEmail(customer)) {
            throw new DuplicatedEmailException();
        }
    }

    public CustomerDetailServiceResponse findById(final Long id) {
        final Customer customer = findCustomerById(id);
        return CustomerDetailServiceResponse.from(customer);
    }

    private Customer findCustomerById(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

    public void updateProfile(final CustomerProfileUpdateServiceRequest request) {
        final Customer customer = findCustomerById(request.getId());
        final Customer updatedCustomer = customer.updateName(request.getName());
        customerDao.update(updatedCustomer);
    }

    public void delete(final CustomerDeleteServiceRequest request) {
        final Customer customer = findCustomerById(request.getId());
        validatePassword(customer, request.getPassword());
        customerDao.deleteById(request.getId());
    }

    public void updatePassword(final CustomerPasswordUpdateServiceRequest request) {
        final Customer customer = findCustomerById(request.getId());
        validatePassword(customer, request.getOldPassword());
        final String newPassword = request.getNewPassword();
        final Customer updatedCustomer = customer.updatePassword(Password.fromRawValue(newPassword, passwordConvertor));
        customerDao.update(updatedCustomer);
    }
}
