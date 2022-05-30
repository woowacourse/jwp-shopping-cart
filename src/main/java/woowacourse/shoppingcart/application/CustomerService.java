package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRegisterRequest;
import woowacourse.shoppingcart.exception.DuplicatedCustomerEmailException;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CustomerDao;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public void registerCustomer(final CustomerRegisterRequest customerRegisterRequest) {
        if (customerDao.existsByEmail(customerRegisterRequest.getEmail())) {
            throw new DuplicatedCustomerEmailException();
        }

        Customer customer = new Customer(customerRegisterRequest.getEmail(),
                customerRegisterRequest.getUserName(),
                customerRegisterRequest.getPassword());
        customerDao.save(customer);
    }
}
