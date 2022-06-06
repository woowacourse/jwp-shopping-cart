package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.application.dto.EmailDuplicationResponse;
import woowacourse.shoppingcart.application.dto.UserNameDuplicationResponse;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.BcryptPasswordEncryptor;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.ui.dto.CustomerRequest;
import woowacourse.shoppingcart.application.dto.CustomerResponse;
import woowacourse.shoppingcart.ui.dto.FindCustomerRequest;
import woowacourse.shoppingcart.ui.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.exception.domain.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.domain.CustomerNotFoundException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long createCustomer(CustomerRequest request) {
        Customer customer = Customer.fromInput(
            request.getName(),
            request.getPassword(),
            request.getEmail(),
            request.getAddress(),
            request.getPhoneNumber()
        ).encryptPassword(new BcryptPasswordEncryptor());

        return customerDao.save(customer)
            .orElseThrow(DuplicateCustomerException::new);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomer(FindCustomerRequest findCustomerRequest) {
        Customer customer = customerDao.findById(findCustomerRequest.getId())
            .orElseThrow(CustomerNotFoundException::new);
        return CustomerResponse.from(customer);
    }

    public void updateCustomer(FindCustomerRequest findCustomerRequest, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerDao.findById(findCustomerRequest.getId())
            .orElseThrow(CustomerNotFoundException::new);
        Customer updatedCustomer = customer.update(updateCustomerRequest.getAddress(),
            updateCustomerRequest.getPhoneNumber());
        customerDao.update(updatedCustomer);
    }

    public void deleteCustomer(FindCustomerRequest findCustomerRequest) {
        customerDao.deleteById(findCustomerRequest.getId());
    }

    public UserNameDuplicationResponse isUserNameDuplicated(String username) {
        return new UserNameDuplicationResponse(username, customerDao.isDuplicated(CustomerDao.COLUMN_USERNAME, username));
    }

    public EmailDuplicationResponse isEmailDuplicated(String email) {
        return new EmailDuplicationResponse(email, customerDao.isDuplicated(CustomerDao.COLUMN_EMAIL, email));
    }
}
