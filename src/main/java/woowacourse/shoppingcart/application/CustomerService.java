package woowacourse.shoppingcart.application;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.application.AuthorizationException;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.domain.UserName;
import woowacourse.shoppingcart.dto.CheckDuplicateRequest;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.DuplicateNameException;
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
        if (customerDao.existsByName(userName)) {
            throw new DuplicateNameException();
        }
        String encryptedPassword = encryptor.encrypt(customerRequest.getPassword());
        Customer customer = new Customer(customerRequest.getName(), encryptedPassword);
        customerDao.save(customer);
    }

    public void deleteCustomerByName(final String customerName) {
        customerDao.deleteByName(customerName);
    }

    public CustomerResponse findCustomerByName(final String userName) {
        final Customer customer = customerDao.findCustomerByName(userName);
        return new CustomerResponse(customer.getName().value());
    }

    public void editCustomerByName(final String userName, final CustomerRequest editRequest) {
        Customer customer = customerDao.findCustomerByName(userName);
        String encryptedPassword = encryptor.encrypt(editRequest.getPassword());
        customer.update(userName, encryptedPassword);
        customerDao.updateByName(userName, customer);
    }

    public void validateNameAndPassword(final String userName, final String password) {
        String encryptedPassword = encryptor.encrypt(password);
        Customer customer = new Customer(userName, encryptedPassword);
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
