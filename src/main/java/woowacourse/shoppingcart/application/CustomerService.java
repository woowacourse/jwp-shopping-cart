package woowacourse.shoppingcart.application;

import java.util.Optional;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long registCustomer(CustomerRequest request) {
        if (isDuplicateEmail(request)) {
            throw new DuplicateCustomerException();
        }
        Customer customer = customerDao.save(new Customer(request.getEmail(), request.getPassword(),
                request.getNickname()));
        return customer.getId();
    }

    private boolean isDuplicateEmail(CustomerRequest request) {
        return customerDao.existByEmail(request.getEmail());
    }

    public Customer findById(Long customerId) {
        return customerDao.findById(customerId)
                .orElseThrow(InvalidCustomerException::new);
    }
}
