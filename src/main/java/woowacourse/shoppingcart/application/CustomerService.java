package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.AuthorizationException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.User.User;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.CheckDuplicateResponse;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.DuplicateNameException;
import woowacourse.shoppingcart.exception.NoExistUserException;
import woowacourse.shoppingcart.exception.NotLoginException;
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
        Customer customer = new Customer(customerRequest.getUserName(), customerRequest.getPassword(), encryptor);
        if (customerDao.existsByName(customer.getName())) {
            throw new DuplicateNameException();
        }
        customerDao.save(customer);
    }

    public void deleteCustomerByName(final User user) {
        if (notExistCustomerName(user.getUserName())) {
            throw new NoExistUserException();
        }
        customerDao.deleteByName(user.getUserName());
    }

    public CustomerResponse findCustomerByName(final User user) {
        final Customer customer = customerDao.findCustomerByName(user.getUserName());
        return new CustomerResponse(customer.getName().value());
    }

    public void editCustomerByName(final User user, final CustomerRequest editRequest) {
        UserName userName = user.getUserName();
        if (notExistCustomerName(userName)) {
            throw new NoExistUserException();
        }

        Customer customer = customerDao.findCustomerByName(userName);
        customer.update(userName.value(), editRequest.getPassword(), encryptor);
        customerDao.updateByName(userName, customer);
    }

    private boolean notExistCustomerName(UserName userName) {
        return !customerDao.existsByName(userName);
    }

    public void validateNameAndPassword(final String userName, final String rawPassword) {
        Customer customer = new Customer(userName, rawPassword, encryptor);
        if (customerDao.checkNotExistsCustomer(customer)) {
            throw new AuthorizationException("로그인에 실패했습니다.");
        }
    }

    public CheckDuplicateResponse isExistUser(final String rawUserName) {
        UserName userName = new UserName(rawUserName);
        return new CheckDuplicateResponse(customerDao.existsByName(userName));
    }
}
