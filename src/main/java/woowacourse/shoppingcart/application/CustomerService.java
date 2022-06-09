package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.FindCustomerRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long createCustomer(CustomerRequest request) {
        Customer customer = new Customer(
                request.getName(),
                request.getPassword(),
                request.getEmail(),
                request.getAddress(),
                request.getPhoneNumber()
        );
        return customerDao.save(customer)
                .orElseThrow(DuplicateCustomerException::new);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomer(FindCustomerRequest findCustomerRequest) {
        Customer customer = customerDao.findByName(findCustomerRequest.getName())
                .orElseThrow(InvalidCustomerException::new);
        return CustomerResponse.from(customer);
    }

    public void updateCustomer(FindCustomerRequest findCustomerRequest, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerDao.findByName(findCustomerRequest.getName())
                .orElseThrow(InvalidCustomerException::new);
        Customer updatedCustomer = customer.update(updateCustomerRequest.getAddress(),
                updateCustomerRequest.getPhoneNumber());
        customerDao.update(updatedCustomer);
    }

    public void deleteCustomer(FindCustomerRequest findCustomerRequest) {
        customerDao.deleteByName(findCustomerRequest.getName());
    }
}
