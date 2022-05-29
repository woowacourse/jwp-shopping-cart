package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.domain.Customer;
import woowacourse.auth.dto.SignUpRequest;
import woowacourse.auth.dto.SignUpResponse;
import woowacourse.shoppingcart.dao.CustomerDao;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public SignUpResponse addCustomer(SignUpRequest signUpRequest) {
        Customer customer = customerDao.save(signUpRequest.toCustomer());
        return SignUpResponse.fromCustomer(customer);
    }
}
