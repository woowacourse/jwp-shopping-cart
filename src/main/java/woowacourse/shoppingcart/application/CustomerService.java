package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Email;
import woowacourse.shoppingcart.dto.CustomerInfoRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.PasswordRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public boolean isDistinctEmail(String email) {
        return !customerDao.existEmail(new Email(email));
    }

    @Transactional
    public Customer signUp(CustomerRequest customerRequest) {
        Customer customer = customerRequest.toCustomer();

        customerDao.save(customer);

        return customer;
    }

    public void checkPassword(String email, PasswordRequest passwordRequest) {
        customerDao.findByEmailAndPassword(new Email(email), passwordRequest.toPassword())
                .orElseThrow(() -> new InvalidCustomerException("비밀번호가 일치하지 않습니다."));
    }

    public Customer findCustomerByEmail(String email) {
        return customerDao.findByEmail(new Email(email)).getCustomer();
    }

    @Transactional
    public void updateInfo(String email, CustomerInfoRequest customerInfoRequest) {
        customerDao.updateInfo(new Email(email), customerInfoRequest.toNickname());
    }

    @Transactional
    public void updatePassword(String email, PasswordRequest passwordRequest) {
        customerDao.updatePassword(new Email(email), passwordRequest.toPassword());
    }
}
