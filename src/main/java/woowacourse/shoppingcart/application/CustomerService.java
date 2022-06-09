package woowacourse.shoppingcart.application;

import static woowacourse.shoppingcart.exception.ExceptionMessage.CODE_2001;
import static woowacourse.shoppingcart.exception.ExceptionMessage.CODE_2202;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.dto.customer.CustomerPasswordUpdateRequest;
import woowacourse.auth.dto.customer.CustomerProfileUpdateRequest;
import woowacourse.auth.dto.customer.SignoutRequest;
import woowacourse.auth.dto.customer.SignupRequest;
import woowacourse.auth.exception.InvalidCustomerException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.exception.DuplicatedEmailException;
import woowacourse.shoppingcart.exception.IncorrectPasswordException;

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
    public Customer updateProfile(String email, CustomerProfileUpdateRequest request) {
        Customer customer = findByEmail(email);
        Customer updatedCustomer = new Customer(customer.getId(),
                customer.getEmail(),
                request.getNickname(),
                customer.getPassword()
        );
        customerDao.update(updatedCustomer);
        return updatedCustomer;
    }

    @Transactional
    public Customer updatePassword(String email, CustomerPasswordUpdateRequest request) {
        Customer customer = findByEmail(email);
        validatePassword(customer, request.getPassword());
        Customer updatedCustomer = new Customer(customer.getId(),
                customer.getEmail(),
                customer.getNickname(),
                request.getNewPassword()
        );
        customerDao.update(updatedCustomer);
        return updatedCustomer;
    }

    private void validateEmailDuplicated(Customer customer) {
        if (customerDao.existByEmail(customer.getEmail())) {
            throw new DuplicatedEmailException(CODE_2001.getMessage());
        }
    }

    private void validatePassword(Customer customer, String password) {
        if (!customer.isSamePassword(password)) {
            throw new IncorrectPasswordException(CODE_2202.getMessage());
        }
    }
}

