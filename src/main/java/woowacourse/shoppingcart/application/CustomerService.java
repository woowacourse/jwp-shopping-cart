package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerSaveRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.LoginCustomer;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerResponse save(CustomerSaveRequest customerSaveRequest) {
        Customer customer = customerDao.save(customerSaveRequest.toCustomer());

        return new CustomerResponse(customer);
    }

    public CustomerResponse find(String username) {
        Customer customer = getCustomer(username);

        return new CustomerResponse(customer);
    }

    public void update(LoginCustomer loginCustomer, CustomerUpdateRequest customerUpdateRequest) {
        String username = loginCustomer.getUsername();
        Customer customer = getCustomer(username);

        customer.modify(customerUpdateRequest.getAddress(), customerUpdateRequest.getPhoneNumber());

        customerDao.update(customer);
    }

    private Customer getCustomer(String username) {
        return customerDao.findByUsername(username)
                .orElseThrow(InvalidCustomerException::new);
    }
}
