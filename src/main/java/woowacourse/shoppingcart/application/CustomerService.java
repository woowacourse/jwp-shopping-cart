package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerSignUpRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public void save(final CustomerSignUpRequest request) {
        if (customerDao.existCustomerByUsername(request.getUsername())) {
            throw new InvalidCustomerException("이미 존재하는 유저 이름입니다.");
        }
        customerDao.save(request.toCustomer());
    }

    public CustomerResponse findByUsername(final String username) {
        return CustomerResponse.from(customerDao.findByUsername(username));
    }
}
