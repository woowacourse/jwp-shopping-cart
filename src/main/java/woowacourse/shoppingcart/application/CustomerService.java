package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerRequest;
import woowacourse.shoppingcart.dto.CustomerResponse;
import woowacourse.shoppingcart.exception.DuplicateNameException;
import woowacourse.shoppingcart.support.Encryptor;

@Service
@Transactional
public class CustomerService {
    private final CustomerDao customerDao;
    private final Encryptor encryptor;

    public CustomerService(final CustomerDao customerDao, final Encryptor encryptor) {
        this.customerDao = customerDao;
        this.encryptor = encryptor;
    }

    public void addCustomer(final CustomerRequest customerRequest) {
        if (customerDao.existsByName(customerRequest.getName())) {
            throw new DuplicateNameException();
        }
        String encryptedPassword = encryptor.encrypt(customerRequest.getPassword());
        customerDao.save(customerRequest.getName(), encryptedPassword);
    }

    public void deleteCustomer(final String userName) {
        customerDao.deleteByName(userName);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomer(final String userName) {
        final Customer customer = customerDao.findCustomerByName(userName);
        return new CustomerResponse(customer.getName());
    }

    public void editCustomer(final String userName, final CustomerRequest editRequest) {
        String encryptedPassword = encryptor.encrypt(editRequest.getPassword());
        customerDao.updateByName(userName, encryptedPassword);
    }

    @Transactional(readOnly = true)
    public boolean existsCustomer(String userName) {
        return customerDao.existsByName(userName);
    }
}
