package woowacourse.shoppingcart.application;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.AuthorizationException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.Password;
import woowacourse.shoppingcart.domain.UserName;
import woowacourse.shoppingcart.dto.CheckDuplicateRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.DuplicateNameException;
import woowacourse.shoppingcart.exception.NoExistUserException;
import woowacourse.shoppingcart.support.Encryptor;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerService {

    private final CustomerDao customerDao;
    private final Encryptor encryptor;

    public CustomerService(final CustomerDao customerDao, final Encryptor encryptor) {
        this.customerDao = customerDao;
        this.encryptor = encryptor;
    }

    public void addCustomer(final CustomerRequest customerRequest) {
        UserName userName = new UserName(customerRequest.getName());
        Password password = new Password(customerRequest.getPassword());
        if (customerDao.existsByName(userName)) {
            throw new DuplicateNameException();
        }
        String encryptedRawPassword = password.encrypt(encryptor);
        Customer customer = new Customer(customerRequest.getName(), encryptedRawPassword);
        customerDao.save(customer);
    }

    public void deleteCustomerByName(final String rawUserName) {
        UserName userName = new UserName(rawUserName);
        if (customerDao.existsByName(userName)) {
            customerDao.deleteByName(userName);
            return;
        }
        throw new NoExistUserException();
    }

    public CustomerResponse findCustomerByName(final String rawUserName) {
        UserName userName = new UserName(rawUserName);
        final Customer customer = customerDao.findCustomerByName(userName);
        return new CustomerResponse(customer.getName().value());
    }

    public void editCustomerByName(final String rawUserName, final CustomerRequest editRequest) {
        UserName userName = new UserName(rawUserName);
        if (customerDao.existsByName(userName)) {
            Customer customer = customerDao.findCustomerByName(userName);
            String encryptedPassword = encryptor.encrypt(editRequest.getPassword());
            customer.update(userName.value(), encryptedPassword);
            customerDao.updateByName(userName, customer);
            return;
        }
        throw new NoExistUserException();
    }

    public void validateNameAndPassword(final String userName, final String rawPassword) {
        Password password = new Password(rawPassword);
        String encryptedRawPassword = password.encrypt(encryptor);
        Customer customer = new Customer(userName, encryptedRawPassword);
        if (customerDao.existsCustomer(customer)) {
            return;
        }
        throw new AuthorizationException("로그인에 실패했습니다.");
    }

    public CheckDuplicateResponse isExistUser(final CheckDuplicateRequest request) {
        UserName userName = new UserName(request.getUserName());
        return new CheckDuplicateResponse(customerDao.existsByName(userName));
    }
}
