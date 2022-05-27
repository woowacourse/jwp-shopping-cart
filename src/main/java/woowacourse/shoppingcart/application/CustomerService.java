package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;

@Service
public class CustomerService {

    private final CustomerDao customerDao;


    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

}
