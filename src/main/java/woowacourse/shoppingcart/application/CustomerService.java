package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UserNames;
import woowacourse.shoppingcart.dto.CustomerRequest;
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

    public void addCustomer(CustomerRequest customerRequest) {
        validateUserName(customerRequest);
        Customer customer = Customer.of(
                customerRequest.getUserName(), customerRequest.getPassword(),
                customerRequest.getNickName(), customerRequest.getAge()
        );
        customerDao.save(customer);
    }

    private void validateUserName(CustomerRequest customerRequest) {
        if (isDuplicateUserName(customerRequest.getUserName())) {
            throw new IllegalArgumentException("기존 회원 아이디와 중복되는 아이디입니다.");
        }
    }

    public UserNameDuplicationResponse checkDuplication(UserNameDuplicationRequest userNameDuplicationRequest) {
        boolean isUnique = !isDuplicateUserName(userNameDuplicationRequest.getUsername());
        return new UserNameDuplicationResponse(isUnique);
    }

    private boolean isDuplicateUserName(String userName) {
        UserNames userNames = UserNames.from(customerDao.findAllUserNames());
        return userNames.contains(userName);
    }

    public void updatePassword(Customer customer, PasswordRequest passwordRequest) {
        customer.validatePassword(passwordRequest.getOldPassword());
        Customer updateCustomer = customer.updatePassword(passwordRequest.getNewPassword());
        customerDao.updatePassword(updateCustomer);
    }
}
