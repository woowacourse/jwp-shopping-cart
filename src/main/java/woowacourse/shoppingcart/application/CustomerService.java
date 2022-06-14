package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.auth.support.PasswordEncoder;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerSignUpRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.customer.DuplicateCustomerBadRequestException;
import woowacourse.shoppingcart.exception.customer.InvalidCustomerBadRequestException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long registerCustomer(CustomerSignUpRequest request) {
        if (customerDao.existByEmail(request.getEmail())) {
            throw new DuplicateCustomerBadRequestException();
        }
        String encryptPassword = PasswordEncoder.encrypt(request.getPassword());
        Customer customer = customerDao.save(
                new Customer(request.getEmail(), encryptPassword, request.getNickname()));
        return customer.getId();
    }

    public Customer findById(Long id) {
        return customerDao.findById(id)
                .orElseThrow(InvalidCustomerBadRequestException::new);
    }

    private Customer findByEmail(String email) {
        return customerDao.findByEmail(email)
                .orElseThrow(InvalidCustomerBadRequestException::new);
    }

    public void deleteByEmail(String email) {
        if (!customerDao.existByEmail(email)) {
            throw new InvalidCustomerBadRequestException();
        }
        customerDao.deleteByEmail(email);
    }

    public void updateCustomer(String email, CustomerUpdateRequest request) {
        Customer customer = findByEmail(email);
        String encryptPassword = PasswordEncoder.encrypt(request.getPassword());
        customerDao.update(
                new Customer(
                        customer.getId(), customer.getEmail(), encryptPassword, request.getNickname()));
    }
}
