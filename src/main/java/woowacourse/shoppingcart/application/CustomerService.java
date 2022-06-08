package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.customer.CustomerUpdateRequest;
import woowacourse.auth.dto.customer.SignoutRequest;
import woowacourse.auth.dto.customer.SignupRequest;
import woowacourse.auth.exception.InvalidAuthException;
import woowacourse.auth.exception.InvalidCustomerException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Transactional
    public Customer signUp(SignupRequest request) {
        Customer customer = request.toEntity();
        validateEmailDuplicated(customer);
        return customerDao.save(customer);
    }

    public Customer findByEmail(String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(() -> new InvalidCustomerException("이메일에 해당하는 회원이 존재하지 않습니다"));
    }

    @Transactional
    public void delete(String email, SignoutRequest request) {
        Customer customer = findByEmail(email);
        validatePassword(customer, request.getPassword());
        customerDao.delete(customer.getId());
    }

    @Transactional
    public Customer update(String email, CustomerUpdateRequest request) {
        Customer customer = findByEmail(email);
        validatePassword(customer, request.getPassword());
        Customer updatedCustomer = new Customer(customer.getId(),
                customer.getEmail(),
                request.getNickname(),
                request.getNewPassword()
        );
        customerDao.update(updatedCustomer);
        return updatedCustomer;
    }

    private void validateEmailDuplicated(Customer customer) {
        if (customerDao.existByEmail(customer.getEmail())) {
            throw new InvalidCustomerException("중복된 이메일 입니다.");
        }
    }

    private void validatePassword(Customer customer, String password) {
        if (!customer.isSamePassword(password)) {
            throw new InvalidAuthException("비밀번호가 달라서 수정할 수 없습니다.");
        }
    }
}

