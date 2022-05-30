package woowacourse.shoppingcart.application;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerCreateRequest;

public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long createCustomer(CustomerCreateRequest customerCreateRequest) {
        return customerDao.save(customerCreateRequest.toCustomer());
    }
}
