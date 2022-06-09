package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.LoginCustomer;
import woowacourse.auth.support.CryptoUtils;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;

@Service
@Transactional
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerResponse save(CustomerRequest customerRequest) {
        Customer customer = new Customer(customerRequest.getLoginId(), customerRequest.getName(),
                CryptoUtils.encrypt(customerRequest.getPassword()));
        Long customerId = customerDao.save(customer);

        Customer savedCustomer = customerDao.findById(customerId);
        return CustomerResponse.of(savedCustomer);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findByLoginId(LoginCustomer loginCustomer) {
        Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        return CustomerResponse.of(customer);
    }

    public CustomerResponse update(LoginCustomer loginCustomer, CustomerUpdateRequest request) {
        Customer savedCustomer = customerDao.findByLoginId(loginCustomer.getLoginId());
        savedCustomer.checkPasswordWithEncryption(request.getPassword());

        customerDao.update(new Customer(loginCustomer.getLoginId(), request.getName(), savedCustomer.getPassword()));
        Customer updatedCustomer = customerDao.findByLoginId(loginCustomer.getLoginId());
        return CustomerResponse.of(updatedCustomer);
    }

    public void delete(LoginCustomer loginCustomer, CustomerDeleteRequest customerDeleteRequest) {
        Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        customer.checkPasswordWithEncryption(customerDeleteRequest.getPassword());
        customerDao.delete(loginCustomer.getLoginId());
    }
}
