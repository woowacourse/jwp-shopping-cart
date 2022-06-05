package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.Password;
import woowacourse.shoppingcart.domain.customer.PasswordEncoder;
import woowacourse.shoppingcart.domain.customer.PlainPassword;
import woowacourse.shoppingcart.dto.customer.CustomerResponse;
import woowacourse.shoppingcart.dto.customer.CustomerSignUpRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdatePasswordRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
@Transactional(readOnly = true)
public class CustomerService {

    private final CustomerDao customerDao;
    private final PasswordEncoder passwordEncoder;

    public CustomerService(final CustomerDao customerDao, final PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void save(final CustomerSignUpRequest request) {
        if (customerDao.existCustomerByUsername(request.getUsername())) {
            throw new InvalidCustomerException("이미 존재하는 유저 이름입니다.");
        }
        PlainPassword plainPassword = new PlainPassword(request.getPassword());
        Customer customer = request.toCustomeWithPassword(passwordEncoder.encode(plainPassword));
        customerDao.save(customer);
    }

    public CustomerResponse findByUsername(final String username) {
        return CustomerResponse.from(customerDao.findByUsername(username));
    }

    @Transactional
    public void update(final CustomerUpdateRequest request, final String username) {
        customerDao.update(request.toCustomerWithUsername(username));
    }

    @Transactional
    public void updatePassword(final String username, final CustomerUpdatePasswordRequest request) {
        PlainPassword plainPassword = new PlainPassword(request.getPassword());
        Password encodedPassword = passwordEncoder.encode(plainPassword);

        customerDao.updatePassword(username, encodedPassword);
    }

    @Transactional
    public void deleteByUsername(final String username) {
        customerDao.deleteByUsername(username);
    }
}
