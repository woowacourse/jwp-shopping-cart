package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.EncryptPassword;
import woowacourse.shoppingcart.domain.customer.PasswordEncryptor;
import woowacourse.shoppingcart.domain.customer.PlainPassword;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.DuplicateResponse;
import woowacourse.shoppingcart.exception.DuplicatedNameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncryptor passwordEncryptor;

    public CustomerService(final CustomerDao customerDao, final PasswordEncryptor passwordEncryptor) {
        this.customerDao = customerDao;
        this.passwordEncryptor = passwordEncryptor;
    }

    public Long signUp(final CustomerRequest customerRequest) {
        validateDuplicateName(customerRequest.getUserName());
        final UserName userName = new UserName(customerRequest.getUserName());
        final PlainPassword plainPassword = new PlainPassword(customerRequest.getPassword());
        final EncryptPassword encryptPassword = plainPassword.toEncryptPassword(passwordEncryptor);
        return customerDao.save(userName.getValue(), encryptPassword.getValue());
    }

    private void validateDuplicateName(final String name) {
        if (customerDao.existsByUserName(name)) {
            throw new DuplicatedNameException();
        }
    }

    @Transactional(readOnly = true)
    public CustomerResponse getMeById(final Long id) {
        final Customer customer = getCustomerById(id);
        return new CustomerResponse(customer);
    }

    public CustomerResponse updateById(final Long id, final CustomerRequest customerRequest) {
        final Customer customer = getCustomerById(id);
        customer.validateUserNameChange(customerRequest.getUserName());

        final Customer updatedCustomer = customerDao.update(
                customer.getId(), customerRequest.getUserName(), customerRequest.getPassword());
        return new CustomerResponse(updatedCustomer);
    }

    public void deleteById(final Long id) {
        final Customer customer = getCustomerById(id);
        customerDao.deleteById(customer.getId());
    }

    private Customer getCustomerById(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

    public DuplicateResponse isDuplicateUserName(final String userName) {
        final boolean isDuplicated = customerDao.existsByUserName(userName);
        return new DuplicateResponse(isDuplicated);
    }
}
