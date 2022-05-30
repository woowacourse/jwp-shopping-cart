package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRegisterRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.DuplicatedCustomerEmailException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.infrastructure.jdbc.dao.CustomerDao;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(final CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public Long registerCustomer(final CustomerRegisterRequest customerRegisterRequest) {
        if (customerDao.existsByEmail(customerRegisterRequest.getEmail())) {
            throw new DuplicatedCustomerEmailException();
        }

        final Customer customer = new Customer(customerRegisterRequest.getEmail(),
                customerRegisterRequest.getUserName(),
                customerRegisterRequest.getPassword());
        return customerDao.save(customer);
    }

    public CustomerResponse findById(Long customerId) {
        return customerDao.findById(customerId)
                .map(customer -> new CustomerResponse(customer.getEmail(), customer.getUserName()))
                .orElseThrow(InvalidCustomerException::new);
    }
}
