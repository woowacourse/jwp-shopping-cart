package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.dto.CustomerRequest;
import woowacourse.auth.dto.CustomerResponse;
import woowacourse.auth.utils.Encryptor;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.CustomerInformationValidator;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerResponse register(CustomerRequest customerRequest) {
        CustomerInformationValidator.validatePassword(customerRequest.getPassword());
        final Customer customer = new Customer(customerRequest.getEmail(), customerRequest.getName(),
                customerRequest.getPhone(), customerRequest.getAddress(),
                Encryptor.encrypt(customerRequest.getPassword()));
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
        CustomerInformationValidator.validatePassword(customerRequest.getPassword());
        final Customer customer = new Customer(id, customerRequest.getEmail(), customerRequest.getName(),
                customerRequest.getPhone(), customerRequest.getAddress(),
                Encryptor.encrypt(customerRequest.getPassword()));
        customerDao.edit(customer);
    }

    public void delete(Long id) {
        customerDao.delete(id);
    }

    public boolean validateEmail(String email) {
        return customerDao.findByEmail(email).isPresent();
    }
}
