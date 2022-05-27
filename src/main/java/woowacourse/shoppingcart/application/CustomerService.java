package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;

@Service
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer save(CustomerRequest customerRequest) {
        Customer customer = Customer.of(
            customerRequest.getUsername(),
            customerRequest.getPassword(),
            customerRequest.getPhoneNumber(),
            customerRequest.getAddress()
        );

        return customerDao.save(customer);
    }
}
