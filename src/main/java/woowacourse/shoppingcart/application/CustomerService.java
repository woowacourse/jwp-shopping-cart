package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerSaveRequest;
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
        Customer customer = customerDao.findByUsername(username)
                .orElseThrow(InvalidCustomerException::new);

        return new CustomerResponse(customer);
    }
}
