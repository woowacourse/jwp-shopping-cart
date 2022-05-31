package woowacourse.shoppingcart.repository;

import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.exception.NotFoundException;

@Repository
public class CustomerRepository {

    private final CustomerDao customerDao;

    public CustomerRepository(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer find(Long id) {
        return customerDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."));
    }

    public boolean checkUsernameExistence(String userName) {
        return customerDao.findByUserName(userName).isPresent();
    }

    public Long save(Customer customer) {
        return customerDao.save(customer);
    }

    public void update(Customer customer) {
        customerDao.update(customer);
    }

    public void delete(Customer customer) {
        customerDao.delete(customer);
    }
}
