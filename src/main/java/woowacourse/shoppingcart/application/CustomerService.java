package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.common.exception.UnauthorizedException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.ui.dto.CustomerRequest;
import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.shoppingcart.ui.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.ui.dto.PasswordRequest;
import woowacourse.shoppingcart.ui.dto.PhoneNumberRequest;
import woowacourse.shoppingcart.dao.entity.CustomerEntity;
import woowacourse.shoppingcart.exception.DuplicatedCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional
public class CustomerService {

    private static final String MISMATCHED_PASSWORD_ERROR = "비밀번호가 일치하지 않습니다.";

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void create(CustomerRequest customerRequest) {
        if (customerDao.existsByAccount(customerRequest.getAccount())) {
            throw new DuplicatedCustomerException();
        }
        Customer customer = customerRequest.toCustomer();
        customerDao.save(CustomerEntity.from(customer));
    }

    @Transactional(readOnly = true)
    public CustomerResponse findById(Long customerId) {
        CustomerEntity customerEntity = getCustomer(customerId);
        return CustomerResponse.from(customerEntity.toCustomer());
    }

    public void delete(Long customerId, PasswordRequest passwordRequest) {
        CustomerEntity customerEntity = getCustomer(customerId);
        Customer customer = customerEntity.toCustomer();

        if (customer.isNotSamePassword(passwordRequest.getPassword())) {
            throw new UnauthorizedException(MISMATCHED_PASSWORD_ERROR);
        }

        customerDao.deleteById(customerId);
    }

    public void update(Long customerId, CustomerUpdateRequest customerUpdateRequest) {
        CustomerEntity customerEntity = getCustomer(customerId);

        Customer customer = customerEntity.toCustomer();
        updateCustomer(customer, customerUpdateRequest);

        customerDao.update(CustomerEntity.from(customer));
    }

    private CustomerEntity getCustomer(Long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
    }

    private void updateCustomer(Customer customer, CustomerUpdateRequest customerUpdateRequest) {
        customer.changeNickname(customerUpdateRequest.getNickname());
        customer.changeAddress(customerUpdateRequest.getAddress());

        PhoneNumberRequest phoneNumberRequest = customerUpdateRequest.getPhoneNumber();
        customer.changePhoneNumber(
                phoneNumberRequest.getStart(),
                phoneNumberRequest.getMiddle(),
                phoneNumberRequest.getLast()
        );
    }
}
