package woowacourse.shoppingcart.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.specification.CustomerSpecification;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.notfound.InValidPassword;
import woowacourse.shoppingcart.exception.notfound.InvalidCustomerException;
import woowacourse.utils.CryptoUtils;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;
    private final CustomerSpecification customerSpec;

    @Transactional
    public Long save(CustomerCreateRequest request) {
        Customer customer = request.toEntity();
        customerSpec.validateForSave(customer);
        encryptPassword(request);

        return customerDao.save(request.toEntity());
    }

    @Transactional
    public Customer findById(long id) {
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

    public Customer findByEmail(String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(InvalidCustomerException::new);
    }

    public Customer findByEmailAndPassword(String email, String password) {
        return customerDao.findByEmailAndPassword(email, password)
                .orElseThrow(InvalidCustomerException::new);
    }

    @Transactional
    public void update(Long id, CustomerUpdateRequest request) {
        if (isSameOriginUsername(id, request)) {
            return;
        }
        customerSpec.validateForUpdate(request.toEntity());


        customerDao.update(id, request.getUsername());
    }

    private boolean isSameOriginUsername(Long id, CustomerUpdateRequest request) {
        Customer foundCustomer = findById(id);
        return foundCustomer.getUsername().equals(request.getUsername());
    }

    @Transactional
    public void delete(Long customerId, String password) {
        customerSpec.validateCustomerExists(customerId);
        String encryptPassword = encryptPassword(password);
        validatePasswordMatches(customerId);
        customerDao.deleteById(customerId, encryptPassword);
    }

    private void validatePasswordMatches(Long customerId) {
        customerDao
                .findById(customerId)
                .orElseThrow(() -> new InValidPassword());
    }

    private String encryptPassword(String password) {
        return CryptoUtils.encrypt(password);
    }

    private void encryptPassword(CustomerCreateRequest request) {
        String originPassword = request.getPassword();
        String encryptPassword = CryptoUtils.encrypt(originPassword);
        request.setPassword(encryptPassword);
    }
}
