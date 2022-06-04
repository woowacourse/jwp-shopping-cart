package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.application.dto.*;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
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

    public void updateName(final CustomerProfileUpdateServiceRequest request) {
        final Customer customer = findCustomerById(request.getId());
        final Customer updatedCustomer = customer.updateName(request.getName());
        customerDao.updateById(updatedCustomer);
    }

    public void delete(final CustomerDeleteServiceRequest request) {
        final Customer customer = findCustomerById(request.getId());
        customer.checkPasswordMatch(request.getPassword());
        customerDao.deleteById(request.getId());
    }

    public void updatePassword(final CustomerPasswordUpdateServiceRequest request) {
        final Customer customer = findCustomerById(request.getId());
        customer.checkPasswordMatch(request.getOldPassword());
        final String newPassword = request.getNewPassword();
        final Customer updatedCustomer = customer.updatePassword(Password.fromRawValue(newPassword));
        customerDao.updateById(updatedCustomer);
    }
}
