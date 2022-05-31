package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.common.exception.BadRequestException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.entity.CustomerEntity;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void create(CustomerRequest customerRequest) {
        if (customerDao.existsByAccount(customerRequest.getAccount())) {
            throw new BadRequestException("이미 존재하는 계정입니다.");
        }
        Customer customer = customerRequest.toCustomer();
        customerDao.save(CustomerEntity.from(customer));
    }
}
