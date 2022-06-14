package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.exception.NoSuchEmailException;
import woowacourse.auth.exception.PasswordNotMatchException;
import woowacourse.shoppingcart.application.dto.CustomerDeleteServiceRequest;
import woowacourse.shoppingcart.application.dto.CustomerPasswordUpdateServiceRequest;
import woowacourse.shoppingcart.application.dto.CustomerProfileUpdateServiceRequest;
import woowacourse.shoppingcart.application.dto.CustomerSaveServiceRequest;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.HashedPassword;
import woowacourse.shoppingcart.domain.customer.RawPassword;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;
import woowacourse.shoppingcart.exception.notfound.CustomerNotFoundException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public long validateCustomer(final String email, final String password) {
        final Customer customer = customerDao.findByEmail(email)
                .orElseThrow(NoSuchEmailException::new);
        validatePassword(customer, password);
        return customer.getId();
    }

    private void validatePassword(final Customer customer, final String password) {
        if (customer.unMatchPasswordWith(password)) {
            throw new PasswordNotMatchException();
        }
    }

    public void save(final CustomerSaveServiceRequest customerSaveServiceRequest) {
        final Customer customer = new Customer(
                customerSaveServiceRequest.getName(),
                customerSaveServiceRequest.getEmail(),
                HashedPassword.from(new RawPassword(customerSaveServiceRequest.getPassword()))
        );

        validateDuplicatedEmail(customer);
        customerDao.save(customer);
    }

    private void validateDuplicatedEmail(final Customer customer) {
        if (customerDao.existsByEmail(customer)) {
            throw new DuplicatedEmailException();
        }
    }

    public Customer findById(final Long id) {
        return findCustomerById(id);
    }

    private Customer findCustomerById(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(CustomerNotFoundException::new);
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
        final Customer updatedCustomer = customer.updatePassword(HashedPassword.from(new RawPassword(newPassword)));
        customerDao.update(updatedCustomer);
    }
}
