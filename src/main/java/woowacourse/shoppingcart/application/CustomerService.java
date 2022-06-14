package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerNameResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void checkDuplicationEmail(String email) {
        validateDuplicationEmail(email);
    }

    private void validateDuplicationEmail(String email) {
        if (customerDao.existByEmail(email)) {
            throw new IllegalArgumentException("중복된 email 입니다.");
        }
    }

    @Transactional
    public CustomerResponse save(CustomerRequest customerRequest) {
        validateDuplicationEmail(customerRequest.getEmail());
        Customer customer = customerRequest.createCustomer();
        customerDao.save(customer);
        return CustomerResponse.from(customer);
    }

    public CustomerResponse findById(Long customerId) {
        checkExistById(customerId);
        Customer customer = customerDao.findById(customerId);
        return CustomerResponse.from(customer);
    }

    public CustomerNameResponse findNameById(Long customerId) {
        checkExistById(customerId);
        String name = customerDao.findNameById(customerId);
        return new CustomerNameResponse(name);
    }

    private void checkExistById(Long customerId) {
        if (!customerDao.existById(customerId)) {
            throw new InvalidCustomerException();
        }
    }

    @Transactional
    public void update(Long customerId, CustomerRequest customerRequest) {
        checkExistById(customerId);
        Customer customer = customerRequest.createCustomer(customerId);
        customerDao.update(customer);
    }

    @Transactional
    public void delete(Long customerId) {
        checkExistById(customerId);
        customerDao.deleteById(customerId);
    }
}
