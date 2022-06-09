package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.EncodedPassword;
import woowacourse.shoppingcart.domain.customer.UnEncodedPassword;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.DuplicateEmailException;
import woowacourse.shoppingcart.exception.DuplicateUsernameException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;
import woowacourse.shoppingcart.exception.NotMatchPasswordException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(CustomerDao customerDao, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    public Long save(CustomerCreateRequest request) {
        validateUsernameDuplication(request.getUsername());
        validateEmailDuplication(request.getEmail());

        UnEncodedPassword password = new UnEncodedPassword(request.getPassword());
        request.setPassword(passwordEncoder.encode(password).getValue());

        return customerDao.save(request);
    }

    public Customer getById(long id) {
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerException::new);
    }

    public Customer getByEmail(String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(InvalidCustomerException::new);
    }

    public Customer getByEmailAndPassword(String email, String password) {
        return customerDao.findByEmailAndPassword(email, password)
                .orElseThrow(InvalidCustomerException::new);
    }

    public void update(Long id, CustomerUpdateRequest request) {
        if (isSameOriginUsername(id, request)) {
            return;
        }
        validateUsernameDuplication(request.getUsername());

        customerDao.update(id, request.getUsername());
    }

    private boolean isSameOriginUsername(Long id, CustomerUpdateRequest request) {
        Customer foundCustomer = getById(id);
        return foundCustomer.getUsername().equals(request.getUsername());
    }

    public void delete(Long id) {
        validateCustomerExists(id);
        customerDao.delete(id);
    }

    private void validateUsernameDuplication(String username) {
        boolean existCustomerBySameUsername = customerDao.findByUsername(username).isPresent();
        if (existCustomerBySameUsername) {
            throw new DuplicateUsernameException();
        }
    }

    private void validateEmailDuplication(String email) {
        boolean existCustomerBySameEmail = customerDao.findByEmail(email).isPresent();
        if (existCustomerBySameEmail) {
            throw new DuplicateEmailException();
        }
    }

    private void validateCustomerExists(Long id) {
        getById(id);
    }

    public void checkSamePassword(Customer customer, UnEncodedPassword enteredPassword) {
        EncodedPassword encodedPassword = passwordEncoder.encode(enteredPassword);
        if (!customer.isSamePassword(encodedPassword)) {
            throw new NotMatchPasswordException();
        }
    }
}
