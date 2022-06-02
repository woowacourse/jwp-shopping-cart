package woowacourse.shoppingcart.application;

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
//        Customer customer = customerRequest.toCustomer();
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
        String encryptedPassword = CryptoUtils.encrypt(request.getPassword());
        checkUpdatable(savedCustomer, encryptedPassword);

        customerDao.update(new Customer(loginCustomer.getLoginId(), request.getName(), encryptedPassword));
        Customer updatedCustomer = customerDao.findByLoginId(loginCustomer.getLoginId());
        return CustomerResponse.of(updatedCustomer);
    }

    private void checkUpdatable(Customer customer, String password) {
        if (!customer.isSamePassword(password)) {
            throw new IllegalArgumentException("비밀번호는 변경할 수 없습니다.");
        }
    }

    public void delete(LoginCustomer loginCustomer, CustomerDeleteRequest customerDeleteRequest) {
        Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        String encryptedPassword = CryptoUtils.encrypt(customerDeleteRequest.getPassword());
        if (!customer.isSamePassword(encryptedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        customerDao.delete(loginCustomer.getLoginId());
    }
}
