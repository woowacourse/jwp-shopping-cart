package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.Encryption;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.values.password.EncryptedPassword;
import woowacourse.shoppingcart.domain.customer.values.password.PlainPassword;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerSignUpRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CustomerService {

    private final CustomerDao customerDao;
    private final Encryption encryption;

    public CustomerService(final CustomerDao customerDao, Encryption encryption) {
        this.customerDao = customerDao;
        this.encryption = encryption;
    }

    public void save(final CustomerSignUpRequest request) {
        if (customerDao.existCustomerByUsername(request.getUsername())) {
            throw new InvalidCustomerException("이미 존재하는 유저 이름입니다.");
        }
        customerDao.save(request.toCustomer(encodePassword(request.getPassword())));
    }

    private EncryptedPassword encodePassword(String plainPassword) {
        return encryption.encrypt(new PlainPassword(plainPassword));
    }

    public CustomerResponse findById(final Long customerId) {
        return CustomerResponse.from(customerDao.findById(customerId));
    }

    public void update(final CustomerUpdateRequest request, final Long customerId) {
        customerDao.update(request.toCustomerWithId(customerId));
    }

    public void updatePassword(final Long customerId, final CustomerUpdatePasswordRequest request) {
        customerDao.updatePasswordById(customerId, encodePassword(request.getPassword()));
    }

    public void delete(final Long customerId) {
        customerDao.deleteById(customerId);
    }

}
