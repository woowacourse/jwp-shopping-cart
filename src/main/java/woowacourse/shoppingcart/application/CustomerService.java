package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void register(String email, String password, String username) {
        customerDao.save(new Customer(email, password,  username));
    }
}
