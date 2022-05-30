package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.exception.DuplicatedNameException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public Long signUp(CustomerRequest customerRequest) {
        validateDuplicateName(customerRequest.getName());
        return customerDao.save(customerRequest.getName(), customerRequest.getPassword());
    }

    private void validateDuplicateName(String name) {
        if (customerDao.existsByUserName(name)) {
            throw new DuplicatedNameException();
        }
    }
}
