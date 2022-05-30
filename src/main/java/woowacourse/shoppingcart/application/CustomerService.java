package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerSaveRequest;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void save(final CustomerSaveRequest customerSaveRequest) {
        final Customer customer = customerSaveRequest.toEntity();
        customerDao.save(customer);
    }
}
