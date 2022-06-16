package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.AuthService;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.request.CustomerPasswordRequest;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.request.CustomerUpdateRequest;
import woowacourse.shoppingcart.dto.request.LoginCustomer;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.util.HashTool;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final AuthService authService;

    public CustomerService(CustomerDao customerDao,
        AuthService authService) {
        this.customerDao = customerDao;
        this.authService = authService;
    }

    @Transactional
    public CustomerResponse addCustomer(CustomerRequest customerRequest) {
        Customer customer = customerRequest.toCustomer().ofHashPassword(HashTool::hashing);
        Customer savedCustomer = customerDao.save(customer)
            .orElseThrow(DuplicateCustomerException::new);
        return toCustomerResponse(savedCustomer);
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(customer.getLoginId(), customer.getUsername());
    }

    @Transactional
    public CustomerResponse updateCustomer(CustomerUpdateRequest customerUpdateRequest,
        LoginCustomer loginCustomer) {
        authService.checkPassword(loginCustomer.toCustomer(), customerUpdateRequest.getPassword());
        Customer customer = customerUpdateRequest.toCustomer(loginCustomer.getLoginId());
        customerDao.update(customer);
        return CustomerResponse.of(customer);
    }

    @Transactional
    public void deleteCustomer(CustomerPasswordRequest customerPasswordRequest, LoginCustomer loginCustomer) {
        authService.checkPassword(loginCustomer.toCustomer(), customerPasswordRequest.getPassword());
        if (!customerDao.delete(loginCustomer.getId())) {
            throw new InvalidCustomerException();
        }
    }
}
