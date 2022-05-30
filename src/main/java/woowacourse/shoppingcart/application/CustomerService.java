package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UserNames;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.PasswordRequest;

@Transactional
@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        Customer customer = Customer.of(
                customerRequest.getUserName(), customerRequest.getPassword(),
                customerRequest.getNickName(), customerRequest.getAge()
        );
        UserNames userNames = UserNames.from(customerDao.findAllUserNames());
        userNames.add(customerRequest.getUserName());

        Long id = customerDao.save(customer);
        return new CustomerResponse(
                id, customerRequest.getUserName(), customerRequest.getPassword(),
                customerRequest.getNickName(), customerRequest.getAge()
        );
    }

    public void updatePassword(Customer customer, PasswordRequest passwordRequest) {
        customer.validatePassword(passwordRequest.getOldPassword());
        Customer updateCustomer = customer.updatePassword(passwordRequest.getNewPassword());
        customerDao.updatePassword(updateCustomer);
    }
}
