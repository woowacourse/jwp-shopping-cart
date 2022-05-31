package woowacourse.shoppingcart.application;

import org.springframework.stereotype.Service;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.dto.CustomerCreationRequest;
import woowacourse.shoppingcart.exception.DuplicateEmailException;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Long create(CustomerCreationRequest request) {
        boolean existEmail = customerDao.existEmail(request.getEmail());
        if (existEmail) {
            throw new DuplicateEmailException();
        }
        Customer customer = new Customer(request.getNickname(), request.getEmail(), request.getPassword());
        return customerDao.save(customer);
    }

    public Customer getByEmail(String email) {
        return customerDao.findByEmail(email);
    }

    public void delete(Customer customer) {
        customerDao.deleteById(customer.getId());
    }
}
