package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRegisterRequest;
import woowacourse.shoppingcart.dto.CustomerRemoveRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateResponse;
import woowacourse.shoppingcart.exception.DuplicatedCustomerEmailException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.WrongPasswordException;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CustomerDao;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public Long registerCustomer(final CustomerRegisterRequest customerRegisterRequest) {
        validateDuplicatedEmail(customerRegisterRequest);

        final Customer customer = new Customer(customerRegisterRequest.getEmail(),
                customerRegisterRequest.getNickname(),
                customerRegisterRequest.getPassword());
        return customerDao.save(customer);
    }

    public CustomerResponse findById(final Long customerId) {
        final Customer customer = getById(customerId);
        return new CustomerResponse(customer.getEmail(), customer.getNickname());
    }

    @Transactional
    public CustomerUpdateResponse updateCustomer(final Long customerId,
                                                 final CustomerUpdateRequest customerUpdateRequest) {
        final Customer customer = getById(customerId);
        validatePassword(customer, customerUpdateRequest.getPassword());
        customer.update(customerUpdateRequest.getNickname(), customerUpdateRequest.getNewPassword());
        customerDao.update(customer);
        return new CustomerUpdateResponse(customer.getNickname());
    }

    @Transactional
    public void removeCustomer(final Long customerId,
                               final CustomerRemoveRequest customerRemoveRequest) {
        final Customer customer = getById(customerId);
        validatePassword(customer, customerRemoveRequest.getPassword());
        customerDao.deleteById(customerId);
    }

    private void validateDuplicatedEmail(CustomerRegisterRequest customerRegisterRequest) {
        if (customerDao.existsByEmail(customerRegisterRequest.getEmail())) {
            throw new DuplicatedCustomerEmailException();
        }
    }

    private Customer getById(final Long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
    }

    private void validatePassword(final Customer customer, final String password) {
        if (!customer.equalsPassword(password)) {
            throw new WrongPasswordException();
        }
    }
}
