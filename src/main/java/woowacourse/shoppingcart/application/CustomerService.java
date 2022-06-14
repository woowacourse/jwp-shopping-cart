package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerResponse register(CustomerRequest customerRequest) {
        final Customer customer = new Customer(customerRequest.getEmail(), customerRequest.getName(),
                customerRequest.getPhone(), customerRequest.getAddress(), customerRequest.getPassword());
        final Customer savedCustomer = customerDao.save(customer);
        return new CustomerResponse(savedCustomer.getId(), savedCustomer.getEmail(), savedCustomer.getName(),
                savedCustomer.getPhone(), savedCustomer.getAddress());
    }

    public CustomerResponse findCustomerById(Long id) {
        final Customer customer = customerDao.findById(id);
        return new CustomerResponse(customer.getId(), customer.getEmail(), customer.getName(), customer.getPhone(),
                customer.getAddress());
    }

    public void edit(Long id, CustomerRequest customerRequest) {
        final Customer customer = new Customer(id, new Customer(customerRequest.getEmail(), customerRequest.getName(),
                customerRequest.getPhone(), customerRequest.getAddress(),
                customerRequest.getPassword()));
        customerDao.edit(customer);
    }

    public void delete(Long id) {
        customerDao.delete(id);
    }

    public void validateEmail(String email) {
        if (customerDao.findByEmail(email).isPresent()) {
            throw new IllegalArgumentException("중복된 email 입니다.");
        }
    }
}
