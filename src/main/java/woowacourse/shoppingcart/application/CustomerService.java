package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        Customer customer = Customer.of(
                customerRequest.getUserName(),
                customerRequest.getPassword(),
                customerRequest.getNickName(),
                customerRequest.getAge()
        );
        Long id = customerDao.save(customer);
        return new CustomerResponse(
                id,
                customerRequest.getUserName(),
                customerRequest.getPassword(),
                customerRequest.getNickName(),
                customerRequest.getAge()
        );
    }
}
