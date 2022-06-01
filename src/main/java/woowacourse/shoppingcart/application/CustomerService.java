package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Nickname;
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
        return !customerDao.existEmail(email);
    }

    public Customer signUp(CustomerRequest customerRequest) {
        Customer customer = customerRequest.toCustomer();
        customerDao.save(customer.getEmail(), customer.getNickname(), customer.getPassword());

        return customer;
    }

    public void checkPassword(String email, PasswordRequest passwordRequest) {
        customerDao.findByEmailAndPassword(email, passwordRequest.getPassword())
                .orElseThrow(() -> new InvalidCustomerException("비밀번호가 일치하지 않습니다."));
    }

    public Customer findCustomerByEmail(String email) {
        return customerDao.findByEmail(email).getCustomer();
    }

    public void updateInfo(String email, CustomerInfoRequest customerInfoRequest) {
        Nickname nickname = customerInfoRequest.toNickname();
        customerDao.updateInfo(email, nickname.getValue());
    }
}
