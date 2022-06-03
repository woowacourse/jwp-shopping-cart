package woowacourse.shoppingcart.application;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.BcryptPasswordEncryptor;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.FindCustomerRequest;
import woowacourse.shoppingcart.dto.UpdateCustomerRequest;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncoder encoder;

    public CustomerService(CustomerDao customerDao, PasswordEncoder encoder) {
        this.customerDao = customerDao;
        this.encoder = encoder;
    }

    public Long createCustomer(CustomerRequest request) {
        Customer customer = Customer.fromInput(
            request.getName(),
            request.getPassword(),
            request.getEmail(),
            request.getAddress(),
            request.getPhoneNumber()
        ).encryptPassword(new BcryptPasswordEncryptor(encoder));

        return customerDao.save(customer)
            .orElseThrow(DuplicateCustomerException::new);
    }

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
        customerDao.deleteById(customerDao.findIdByName(findCustomerRequest.getName()));
    }
}
