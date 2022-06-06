package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.auth.support.Encoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.user.Customer;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.UserUpdateRequest;
import woowacourse.shoppingcart.exception.badrequest.DuplicateUserException;
import woowacourse.shoppingcart.exception.badrequest.InvalidUserException;

@Service
@Transactional
public class UserService {

    private final CustomerDao customerDao;
    private final Encoder encoder;

    public UserService(CustomerDao customerDao, Encoder encoder) {
        this.customerDao = customerDao;
        this.encoder = encoder;
    }

    public Long registerCustomer(SignUpRequest request) {
        if (customerDao.existByEmail(request.getEmail())) {
            throw new DuplicateUserException();
        }
        String encryptPassword = encoder.encrypt(request.getPassword());
        Customer customer = customerDao.save(
                new Customer(request.getEmail(), encryptPassword, request.getNickname()));
        return customer.getId();
    }

    public Customer findByEmail(String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(InvalidUserException::new);
    }

    public void deleteByEmail(String email) {
        if (!customerDao.existByEmail(email)) {
            throw new InvalidUserException();
        }
        customerDao.deleteByEmail(email);
    }

    public void updateCustomer(String email, UserUpdateRequest request) {
        Customer customer = findByEmail(email);
        String encryptPassword = encoder.encrypt(request.getPassword());
        customerDao.update(new Customer(customer.getId(), customer.getEmail(), encryptPassword,
                request.getNickname()));
    }
}
