package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.util.HashTool;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
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
    public CustomerResponse updateCustomer(CustomerUpdateRequest customerUpdateRequest, String loginId) {
        if (!customerDao.existByLoginId(loginId)) {
            throw new InvalidCustomerException();
        }

        if (customerDao.existByUsername(customerUpdateRequest.getName())) {
            throw new IllegalArgumentException("이미 존재하는 유저네임입니다.");
        }
        Customer customer = customerUpdateRequest.toCustomer(loginId);
        customerDao.update(customer);
        return CustomerResponse.of(customer);
    }

    @Transactional
    public void deleteCustomer(String loginId) {
        if (!customerDao.existByLoginId(loginId)) {
            throw new InvalidCustomerException();
        }
        customerDao.delete(loginId);
    }

    public void checkPassword(Customer customer, String password) {
        String hashedPassword = HashTool.hashing(password);

        if (!customer.isSamePassword(hashedPassword)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
