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
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void validateEmailDuplication(String email) {
        if (customerDao.existByEmail(email)) {
            throw new IllegalArgumentException("중복된 email 입니다.");
        }
    }

    public CustomerResponse save(CustomerRequest customerRequest) {
        validateEmailDuplication(customerRequest.getEmail());
        Customer customer = customerRequest.createCustomer();
        Long id = customerDao.save(customer);
        return CustomerResponse.of(id, customer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse find(Long customerId) {
        checkExistById(customerId);
        Customer customer = customerDao.findById(customerId);
        return CustomerResponse.of(customer);
    }

    private void checkExistById(Long customerId) {
        if (!customerDao.existById(customerId)) {
            throw new InvalidCustomerException();
        }
    }

    public void update(Long customerId, CustomerRequest customerRequest) {
        checkExistById(customerId);
        Customer customer = customerRequest.createCustomer(customerId);
        customerDao.update(customer);
    }

    public void delete(Long customerId) {
        checkExistById(customerId);
        customerDao.deleteById(customerId);
    }

    public CustomerNameResponse findCustomerName(Long customerId) { // TODO : test
        return new CustomerNameResponse(customerDao.findNameById(customerId));
    }
}
