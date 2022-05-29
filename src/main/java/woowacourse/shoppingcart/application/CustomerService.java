package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.SignupRequest;
import woowacourse.shoppingcart.exception.DuplicatedAccountException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void createCustomer(SignupRequest signupRequest) {
        if (customerDao.findByAccount(signupRequest.getAccount()).isPresent()) {
            throw new DuplicatedAccountException();
        }
        customerDao.save(signupRequest.toEntity());
    }
}
