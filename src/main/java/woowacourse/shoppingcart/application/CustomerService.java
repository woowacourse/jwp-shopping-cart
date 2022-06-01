package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.LoginCustomer;
import woowacourse.shoppingcart.dto.CustomerDeleteRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public CustomerResponse save(CustomerRequest customerRequest) {
        Customer customer = customerRequest.toCustomer();
        Long customerId = customerDao.save(customer);

        Customer savedCustomer = customerDao.findById(customerId);
        return CustomerResponse.of(savedCustomer);
    }

    public CustomerResponse findByLoginId(LoginCustomer loginCustomer) {
        Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        return CustomerResponse.of(customer);
    }

    public CustomerResponse update(LoginCustomer loginCustomer, CustomerRequest customerRequest) {
        Customer savedCustomer = customerDao.findByLoginId(loginCustomer.getLoginId());
        checkUpdatable(savedCustomer, customerRequest);

        customerDao.update(customerRequest.toCustomer());
        Customer updatedCustomer = customerDao.findByLoginId(loginCustomer.getLoginId());
        return CustomerResponse.of(updatedCustomer);
    }

    private void checkUpdatable(Customer customer, CustomerRequest request) {
        if (!customer.isSameLoginId(request.getLoginId())) {
            throw new IllegalArgumentException("아이디는 변경할 수 없습니다.");
        }
        if (!customer.isSamePassword(request.getPassword())) {
            throw new IllegalArgumentException("비밀번호는 변경할 수 없습니다.");
        }
    }

    public void delete(LoginCustomer loginCustomer, CustomerDeleteRequest customerDeleteRequest) {
        Customer customer = customerDao.findByLoginId(loginCustomer.getLoginId());
        if (!customer.isSamePassword(customerDeleteRequest.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        customerDao.delete(loginCustomer.getLoginId());
    }
}
