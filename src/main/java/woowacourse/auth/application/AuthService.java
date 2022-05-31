package woowacourse.auth.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@Service
public class AuthService {

    private final CustomerDao customerDao;

    public AuthService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerResponse save(CustomerRequest customerRequest) {
        validateDuplicationEmail(customerRequest);
        Customer customer = customerRequest.createCustomer();
        customerDao.save(customer);
        return CustomerResponse.from(customer);
    }

    private void validateDuplicationEmail(CustomerRequest customerRequest) {
        if (customerDao.existEmail(customerRequest.getEmail())) {
            throw new IllegalArgumentException("중복된 email 입니다.");
        }
    }
}
