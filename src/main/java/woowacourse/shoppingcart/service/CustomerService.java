package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.request.CustomerRequest;
import woowacourse.shoppingcart.dto.response.CheckDuplicationResponse;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.exception.duplicate.DuplicateCustomerException;
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
        if (customerDao.existsByName(customerRequest.getUserName())) {
            throw new DuplicateCustomerException();
        }
        final String encryptedPassword = encryptor.encrypt(customerRequest.getPassword());
        customerDao.save(new Customer(customerRequest.getUserName(), encryptedPassword));
    }

    public void deleteCustomerByName(final String customerName) {
        customerDao.deleteByName(customerName);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomerByName(final String customerName) {
        final Customer customer = customerDao.getByName(customerName);
        return new CustomerResponse(customer.getUserName());
    }

    public void editCustomerByName(final String customerName, final CustomerRequest editRequest) {
        final String encryptedPassword = encryptor.encrypt(editRequest.getPassword());
        customerDao.updatePasswordByName(customerName, encryptedPassword);
    }

    @Transactional(readOnly = true)
    public CheckDuplicationResponse checkDuplicationByName(final String userName) {
        return new CheckDuplicationResponse(customerDao.existsByName(userName));
    }
}
