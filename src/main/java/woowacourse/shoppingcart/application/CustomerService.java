package woowacourse.shoppingcart.application;

import java.util.Optional;
import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long registCustomer(CustomerRequest request) {
        Customer customer = customerDao.save(
                new Customer(request.getEmail(), request.getPassword(), request.getNickname()));
        return customer.getId();
    }

    public Customer findById(Long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
    }
}
