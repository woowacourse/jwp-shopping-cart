package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerResponse save(CustomerRequest customerRequest) {
        Customer customer = customerRequest.toCustomer();
        Long customerId = customerDao.save(customer);

        Customer savedCustomer = customerDao.findById(customerId);
        return CustomerResponse.of(savedCustomer);
    }
}
