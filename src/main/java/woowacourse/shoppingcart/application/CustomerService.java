package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long createCustomer(CustomerRequest request) {
        Customer customer = new Customer(
                request.getUsername(),
                request.getPassword(),
                request.getEmail(),
                request.getAddress(),
                request.getPhoneNumber()
        );
        return customerDao.save(customer);
    }
}
