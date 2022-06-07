package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.application.dto.*;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.EncodedPassword;
import woowacourse.shoppingcart.domain.PlainPassword;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void save(final CustomerSaveServiceRequest customerSaveServiceRequest) {
        final Customer customer = customerSaveServiceRequest.toEntity();

        validateDuplicatedEmail(customer);
        customerDao.save(customer);
    }

    public CustomerDetailServiceResponse findById(final Long id) {
        final Customer customer = findCustomerById(id);
        return CustomerDetailServiceResponse.from(customer);
    }

    @Transactional
    public void updateName(final CustomerProfileUpdateServiceRequest request) {
        final Customer customer = findCustomerById(request.getId());
        final Customer updatedCustomer = customer.updateName(request.getName());
        customerDao.updateById(updatedCustomer);
    }

    @Transactional
    public void delete(final CustomerDeleteServiceRequest request) {
        final Customer customer = findCustomerById(request.getId());
        customer.checkPasswordMatch(request.getPassword());
        customerDao.deleteById(request.getId());
    }

    @Transactional
    public void updatePassword(final CustomerPasswordUpdateServiceRequest request) {
        final Customer customer = findCustomerById(request.getId());
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
