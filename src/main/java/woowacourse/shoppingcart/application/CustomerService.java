package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.exception.AuthException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Email;
import woowacourse.shoppingcart.dto.customer.CustomerProfileRequest;
import woowacourse.shoppingcart.dto.customer.CustomerRequest;
import woowacourse.shoppingcart.dto.customer.PasswordRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public boolean isDistinctEmail(final String email) {
        return !customerDao.existEmail(new Email(email));
    }

    @Transactional
    public Customer signUp(final CustomerRequest customerRequest) {
        final Customer customer = customerRequest.toCustomer();

        customerDao.save(customer);

        return customer;
    }

    public boolean checkPassword(final String emailValue, final PasswordRequest passwordRequest) {
        Email email = new Email(emailValue);
        validateExists(email);
        customerDao.findByEmailAndPassword(email, passwordRequest.toPassword())
                .orElseThrow(() -> new InvalidCustomerException("비밀번호가 일치하지 않습니다."));
        return true;
    }

    public Customer findByEmail(final String emailValue) {
        Email email = new Email(emailValue);
        validateExists(email);
        return customerDao.findByEmail(email).getCustomer();
    }

    @Transactional
    public void updateProfile(final String emailValue, final CustomerProfileRequest customerProfileRequest) {
        Email email = new Email(emailValue);
        validateExists(email);
        customerDao.updateProfile(email, customerProfileRequest.toNickname());
    }

    @Transactional
    public void updatePassword(final String emailValue, final PasswordRequest passwordRequest) {
        Email email = new Email(emailValue);
        validateExists(email);
        customerDao.updatePassword(email, passwordRequest.toPassword());
    }

    @Transactional
    public void delete(final String emailValue) {
        Email email = new Email(emailValue);
        validateExists(email);
        customerDao.delete(email);
    }

    private void validateExists(final Email email) {
        if (!customerDao.existEmail(email)) {
            throw new AuthException();
        }
    }
}
