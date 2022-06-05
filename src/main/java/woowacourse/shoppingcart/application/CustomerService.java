package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.AuthService;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerPasswordRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.util.HashTool;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final AuthService authService;

    public CustomerService(CustomerDao customerDao, AuthService authService) {
        this.customerDao = customerDao;
        this.authService = authService;
    }

    @Transactional
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        if (customerDao.existByLoginId(customerRequest.getLoginId())) {
            throw new IllegalArgumentException("이미 존재하는 id입니다.");
        }
        Customer customer = customerRequest.toCustomer().ofHashPassword(HashTool::hashing);
        Customer savedCustomer = customerDao.save(customer);
        return toCustomerResponse(savedCustomer);
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(customer.getLoginId(), customer.getUsername());
    }

    @Transactional
    public CustomerResponse updateCustomer(CustomerUpdateRequest customerUpdateRequest, Customer customer) {
        authService.checkPassword(customer, customerUpdateRequest.getPassword());
        if (!customerDao.existByLoginId(customer.getLoginId())) {
            throw new InvalidCustomerException();
        }

        if (customerDao.existByUsername(customerUpdateRequest.getName())) {
            throw new IllegalArgumentException("이미 존재하는 유저네임입니다.");
        }
        Customer updatedCustomer = customerUpdateRequest.toCustomer(customer.getLoginId());
        customerDao.update(updatedCustomer);
        return CustomerResponse.of(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Customer customer, CustomerPasswordRequest customerPasswordRequest) {
        if (!customerDao.existByLoginId(customer.getLoginId())) {
            throw new InvalidCustomerException();
        }
        authService.checkPassword(customer, customerPasswordRequest.getPassword());
        customerDao.delete(customer.getLoginId());
    }
}
