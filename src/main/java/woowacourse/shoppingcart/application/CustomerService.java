package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;

import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.dto.customer.CustomerCreateRequest;
import woowacourse.shoppingcart.dto.customer.CustomerUpdateRequest;
import woowacourse.shoppingcart.exception.DuplicateCustomerException;
import woowacourse.shoppingcart.exception.InvalidCustomerException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long save(CustomerCreateRequest request) {
        validateUsernameDuplication(request.getUsername());
        validateEmailDuplication(request.getEmail());

        return customerDao.save(request.toEntity());
    }

    private void validateUsernameDuplication(String username) {
        boolean existCustomerBySameUsername = customerDao.findByUsername(username).isPresent();
        if (existCustomerBySameUsername) {
            throw new DuplicateCustomerException("username", "이미 가입된 닉네임입니다.");
        }
    }

    private void validateEmailDuplication(String email) {
        boolean existCustomerBySameEmail = customerDao.findByEmail(email).isPresent();
        if (existCustomerBySameEmail) {
            throw new DuplicateCustomerException("email", "이미 가입된 이메일입니다.");
        }
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
        validateUsernameDuplication(request.getUsername());

        customerDao.update(id, request.getUsername());
    }

    private boolean isSameOriginUsername(Long id, CustomerUpdateRequest request) {
        Customer foundCustomer = findById(id);
        return foundCustomer.getUsername().equals(request.getUsername());
    }

    public void delete(Long id) {
        validateCustomerExists(id);
        customerDao.delete(id);
    }

    private void validateCustomerExists(Long id) {
        findById(id);
    }
}
