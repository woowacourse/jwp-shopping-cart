package woowacourse.shoppingcart.customer.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.auth.support.exception.AuthException;
import woowacourse.shoppingcart.auth.support.exception.AuthExceptionCode;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerPasswordUpdateRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerProfileUpdateRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerRegisterRequest;
import woowacourse.shoppingcart.customer.application.dto.request.CustomerRemoveRequest;
import woowacourse.shoppingcart.customer.application.dto.response.CustomerResponse;
import woowacourse.shoppingcart.customer.application.dto.response.CustomerUpdateResponse;
import woowacourse.shoppingcart.customer.domain.Customer;
import woowacourse.shoppingcart.customer.support.exception.CustomerException;
import woowacourse.shoppingcart.customer.support.exception.CustomerExceptionCode;
import woowacourse.shoppingcart.customer.support.jdbc.dao.CustomerDao;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public long registerCustomer(final CustomerRegisterRequest request) {
        if (customerDao.existsByEmail(request.getEmail())) {
            throw new CustomerException(CustomerExceptionCode.ALREADY_EMAIL_EXIST);
        }

        final Customer customer = new Customer(request.getEmail(), request.getNickname(), request.getPassword());
        return customerDao.save(customer);
    }

    public CustomerResponse findById(final long customerId) {
        final Customer customer = getById(customerId);
        return new CustomerResponse(customer.getEmail(), customer.getNickname());
    }

    private Customer getById(final long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(() -> new AuthException(AuthExceptionCode.REQUIRED_AUTHORIZATION));
    }

    @Transactional
    public CustomerUpdateResponse updateCustomerProfile(final long customerId,
                                                        final CustomerProfileUpdateRequest request) {
        final Customer customer = getById(customerId);
        customer.updateProfile(request.getNickname());
        customerDao.update(customer);
        return new CustomerUpdateResponse(customer.getNickname());
    }

    @Transactional
    public void updateCustomerPassword(final long customerId, final CustomerPasswordUpdateRequest request) {
        final Customer customer = getById(customerId);
        customer.updatePassword(request.getPassword(), request.getNewPassword());
        customerDao.update(customer);
    }

    @Transactional
    public void removeCustomer(final long customerId, final CustomerRemoveRequest request) {
        final Customer customer = getById(customerId);
        validatePassword(customer, request.getPassword());
        customerDao.deleteById(customerId);
    }

    private void validatePassword(final Customer customer, final String password) {
        if (customer.isPasswordDisMatch(password)) {
            throw new CustomerException(CustomerExceptionCode.MISMATCH_PASSWORD);
        }
    }
}
