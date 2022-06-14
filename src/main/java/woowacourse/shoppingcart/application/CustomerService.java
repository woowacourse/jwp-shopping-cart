package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.domain.EncodedPassword;
import woowacourse.shoppingcart.domain.PlainPassword;
import woowacourse.shoppingcart.dto.*;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public void save(final CustomerRegisterRequest request) {
        final Email email = new Email(request.getEmail());
        final PlainPassword plainPassword = new PlainPassword(request.getPassword());
        final EncodedPassword encodedPassword = new EncodedPassword(plainPassword.encode());
        final Customer customer = new Customer(request.getName(), email, encodedPassword);
        validateDuplicatedEmail(customer);

        customerDao.save(customer);
    }

    public CustomerDetailResponse findById(final Long id) {
        final Customer customer = findCustomerById(id);
        return new CustomerDetailResponse(customer.getName(), customer.getEmail().getValue());
    }

    @Transactional
    public void updateName(final Long id, final CustomerProfileUpdateRequest request) {
        final Customer customer = findCustomerById(id);
        final Customer updatedCustomer = customer.updateName(request.getName());
        customerDao.updateById(updatedCustomer);
    }

    @Transactional
    public void delete(final Long id, final CustomerDeleteRequest request) {
        final Customer customer = findCustomerById(id);
        customer.checkPasswordMatch(request.getPassword());
        customerDao.deleteById(id);
    }

    @Transactional
    public void updatePassword(final Long id, final CustomerPasswordUpdateRequest request) {
        final Customer customer = findCustomerById(id);
        customer.checkPasswordMatch(request.getOldPassword());
        final PlainPassword plainPassword = new PlainPassword(request.getNewPassword());
        final EncodedPassword encodedPassword = new EncodedPassword(plainPassword.encode());
        final Customer updatedCustomer = customer.updatePassword(encodedPassword);
        customerDao.updateById(updatedCustomer);
    }

    private void validateDuplicatedEmail(final Customer customer) {
        if (customerDao.existsByEmail(customer)) {
            throw new DuplicatedEmailException();
        }
    }

    private Customer findCustomerById(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }
}
