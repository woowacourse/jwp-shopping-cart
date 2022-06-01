package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.FindCustomerRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
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
        return customerDao.save(customer);
    }

    public CustomerResponse findCustomer(FindCustomerRequest findCustomerRequest) {
        Customer customer = customerDao.findByUserName(findCustomerRequest.getName())
                .orElseThrow(InvalidCustomerException::new);
        return CustomerResponse.from(customer);
    }

    public void updateCustomer(FindCustomerRequest findCustomerRequest, UpdateCustomerRequest updateCustomerRequest) {
        Customer customer = customerDao.findByUserName(findCustomerRequest.getName())
                .orElseThrow(InvalidCustomerException::new);
        Customer updatedCustomer = customer.update(updateCustomerRequest.getAddress(), updateCustomerRequest.getPhoneNumber());
        customerDao.update(updatedCustomer);
    }
}
