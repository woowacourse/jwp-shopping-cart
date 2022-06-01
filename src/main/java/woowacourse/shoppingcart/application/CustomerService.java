package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;

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
        Customer customer = customerDao.save(CustomerRequest.toCustomer(customerRequest));
        return toCustomerResponse(customer);
    }

    private CustomerResponse toCustomerResponse(Customer customer) {
        return new CustomerResponse(customer.getLoginId(), customer.getUsername());
    }

    @Transactional
    public CustomerResponse updateCustomer(CustomerRequest customerRequest) {
        if (!customerDao.existByLoginId(customerRequest.getLoginId())) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }

        if (customerDao.existByUsername(customerRequest.getName())) {
            throw new IllegalArgumentException("이미 존재하는 유저네임입니다.");
        }
        customerDao.update(CustomerRequest.toCustomer(customerRequest));
        return CustomerResponse.of(customerRequest);
    }

    @Transactional
    public void deleteCustomer(String loginId) {
        if (!customerDao.existByLoginId(loginId)) {
            throw new IllegalArgumentException("존재하지 않는 회원입니다.");
        }
        customerDao.delete(loginId);
    }

    public void checkPassword(Customer customer, String password) {
        if (!customer.isSamePassword(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
