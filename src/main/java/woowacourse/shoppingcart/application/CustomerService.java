package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.application.dto.CustomerDto;
import woowacourse.shoppingcart.dao.CustomerDao;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long createCustomer(final CustomerDto newCustomer) {
        return customerDao.createCustomer(newCustomer);
    }
}
