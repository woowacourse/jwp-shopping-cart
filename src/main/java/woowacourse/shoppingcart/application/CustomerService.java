package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.exception.PasswordNotMatchException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerDeleteServiceRequest;
import woowacourse.shoppingcart.dto.CustomerDetailServiceResponse;
import woowacourse.shoppingcart.dto.CustomerSaveRequest;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void save(final CustomerSaveRequest customerSaveRequest) {
        final Customer customer = customerSaveRequest.toEntity();
        if (customerDao.existsByEmail(customer)) {
            throw new DuplicatedEmailException();
        }
        customerDao.save(customer);
    }

    public CustomerDetailServiceResponse findById(final Long id) {
        final Customer customer = findCustomerById(id);
        return CustomerDetailServiceResponse.from(customer);
    }

    private Customer findCustomerById(final Long id) {
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

    public void delete(final CustomerDeleteServiceRequest request) {
        final Customer customer = findCustomerById(request.getId());
        validatePassword(customer, request.getPassword());
        customerDao.deleteById(request.getId());
    }

    private void validatePassword(final Customer customer, final String password) {
        if (!customer.isSamePassword(password)) {
            throw new PasswordNotMatchException();
        }
    }
}
