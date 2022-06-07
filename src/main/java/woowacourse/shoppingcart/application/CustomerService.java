package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.dto.UserNameDuplicationRequest;
import woowacourse.shoppingcart.dto.UserNameDuplicationResponse;

@Transactional
@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long addCustomer(CustomerRequest customerRequest) {
        validateUserName(customerRequest);
        Customer customer = Customer.of(
                customerRequest.getUserName(), customerRequest.getPassword(),
                customerRequest.getNickName(), customerRequest.getAge()
        );
        return customerDao.save(customer);
    }

    private void validateUserName(CustomerRequest customerRequest) {
        if (customerDao.isUsernameExist(customerRequest.getUserName())) {
            throw new IllegalArgumentException("기존 회원 아이디와 중복되는 아이디입니다.");
        }
    }

    public UserNameDuplicationResponse checkDuplication(UserNameDuplicationRequest userNameDuplicationRequest) {
        boolean isUnique = !customerDao.isUsernameExist(userNameDuplicationRequest.getUsername());
        return new UserNameDuplicationResponse(isUnique);
    }

    @Transactional(readOnly = true)
    public CustomerResponse getCustomer(String username) {
        Customer customer = customerDao.getCustomerByUserName(username);
        return CustomerResponse.from(customer);
    }

    public void updatePassword(String username, PasswordRequest passwordRequest) {
        Customer customer = customerDao.getCustomerByUserName(username);
        customer.validatePassword(passwordRequest.getOldPassword());
        Customer updateCustomer = customer.updatePassword(passwordRequest.getNewPassword());
        customerDao.updatePassword(updateCustomer);
    }

    public void updateInfo(String username, CustomerRequest customerRequest) {
        Customer customer = customerDao.getCustomerByUserName(username);
        Customer updateCustomer = customer.updateInfo(customerRequest.getNickName(), customerRequest.getAge());
        customerDao.updateInfo(updateCustomer);
    }

    public void deleteCustomer(String username) {
        customerDao.delete(username);
    }
}
