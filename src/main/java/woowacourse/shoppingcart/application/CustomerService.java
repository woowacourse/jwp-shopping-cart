package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.Encoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.user.Customer;
import woowacourse.shoppingcart.dto.SignUpRequest;
import woowacourse.shoppingcart.dto.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;
    private final Encoder encoder;

    public CustomerService(CustomerDao customerDao, Encoder encoder) {
        this.customerDao = customerDao;
        this.encoder = encoder;
    }

    public Long registerCustomer(SignUpRequest request) {
        if (customerDao.existByEmail(request.getEmail())) {
            throw new DuplicateCustomerException();
        }
        String encryptPassword = encoder.encrypt(request.getPassword());
        Customer customer = customerDao.save(
                new Customer(request.getEmail(), encryptPassword, request.getNickname()));
        return customer.getId();
    }

    public Customer findByEmail(String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(InvalidCustomerException::new);
    }

    public void deleteByEmail(String email) {
        if (!customerDao.existByEmail(email)) {
            throw new InvalidCustomerException();
        }
        customerDao.deleteByEmail(email);
    }

    public void updateCustomer(String email, CustomerUpdateRequest request) {
        Customer customer = findByEmail(email);
        String encryptPassword = encoder.encrypt(request.getPassword());
        customerDao.update(new Customer(customer.getId(), customer.getEmail(), encryptPassword,
                request.getNickname()));
    }
}
