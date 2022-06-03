package woowacourse.shoppingcart.application;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import woowacourse.auth.specification.CustomerSpecification;
import woowacourse.utils.CryptoUtils;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerDao customerDao;
    private final CustomerSpecification customerSpec;

    public Long save(CustomerCreateRequest request) {
        customerSpec.validateUsernameDuplicate(request.getUsername());
        customerSpec.validateEmailDuplicate(request.getEmail());
        encryptPassword(request);

        return customerDao.save(request.toEntity());
    }

    private void encryptPassword(CustomerCreateRequest request) {
        String originPassword = request.getPassword();
        String encryptPassword = CryptoUtils.encrypt(originPassword);
        request.setPassword(encryptPassword);
    }

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

    public void update(Long id, CustomerUpdateRequest request) {
        if (isSameOriginUsername(id, request)) {
            return;
        }
        customerSpec.validateUsernameDuplicate(request.getUsername());

        customerDao.update(id, request.getUsername());
    }

    private boolean isSameOriginUsername(Long id, CustomerUpdateRequest request) {
        Customer foundCustomer = findById(id);
        return foundCustomer.getUsername().equals(request.getUsername());
    }

    public void delete(Long id) {
        customerSpec.validateCustomerExists(id);
        customerDao.deleteById(id);
    }
}
