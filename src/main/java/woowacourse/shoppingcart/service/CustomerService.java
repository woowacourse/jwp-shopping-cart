package woowacourse.shoppingcart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.domain.customer.PlainPassword;
import woowacourse.shoppingcart.domain.customer.UserName;
import woowacourse.shoppingcart.dto.request.EditCustomerRequest;
import woowacourse.shoppingcart.dto.request.SignUpRequest;
import woowacourse.shoppingcart.dto.response.CustomerResponse;
import woowacourse.shoppingcart.dto.response.ExistsCustomerResponse;
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

    public void addCustomer(final SignUpRequest request) {
        if (customerDao.existsByName(new UserName(request.getUserName()))) {
            throw new DuplicateCustomerException();
        }
        final Password password = encryptor.encrypt(new PlainPassword(request.getPassword()));
        customerDao.save(new Customer(new UserName(request.getUserName()), password));
    }

    public void deleteCustomerByName(final UserName userName) {
        customerDao.deleteByName(userName);
    }

    @Transactional(readOnly = true)
    public CustomerResponse findCustomerByName(final UserName userName) {
        final Customer customer = customerDao.getByName(userName);
        return new CustomerResponse(customer.getUserName());
    }

    public void editCustomerByName(final UserName userName, final EditCustomerRequest request) {
        final Password password = encryptor.encrypt(new PlainPassword(request.getPassword()));
        customerDao.updatePasswordByName(userName, password);
    }

    @Transactional(readOnly = true)
    public ExistsCustomerResponse existsByName(final String userName) {
        return new ExistsCustomerResponse(customerDao.existsByName(new UserName(userName)));
    }
}
