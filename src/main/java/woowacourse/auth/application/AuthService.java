package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Service
public class AuthService {

    private final CustomerDao customerDao;

    public AuthService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerResponse register(CustomerRequest customerRequest) {
        final Customer customer = new Customer(customerRequest.getEmail(), customerRequest.getName(), customerRequest.getPhone(), customerRequest.getAddress(), customerRequest.getPassword());
        final Customer savedCustomer = customerDao.save(customer);
        return new CustomerResponse(savedCustomer.getId(), savedCustomer.getEmail(), savedCustomer.getName(), savedCustomer.getPhone(), savedCustomer.getAddress());
    }
}
