package woowacourse.shoppingcart.repository;

import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.entity.CustomerEntity;
import woowacourse.shoppingcart.exception.NotFoundException;

@Repository
public class CustomerRepository {

    private final CustomerDao customerDao;

    public CustomerRepository(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public Customer find(Long id) {
        return customerDao.findById(id)
                .orElseThrow(() -> new NotFoundException("존재하지 않는 고객입니다."))
                .toDomain();
    }

    public boolean checkUsernameExistence(String userName) {
        return customerDao.findByUserName(userName).isPresent();
    }

    public Long save(Customer customer) {
        return customerDao.save(toEntity(customer));
    }

    public void update(Long id, Customer customer) {
        customerDao.update(toEntity(id, customer));
    }

    public void delete(Long id, Customer customer) {
        customerDao.delete(toEntity(id, customer));
    }

    private CustomerEntity toEntity(Long id, Customer customer) {
        return new CustomerEntity(id,
                customer.getUsername().getValue(),
                customer.getPassword().getValue(),
                customer.getNickname().getValue(),
                customer.getAge());
    }

    private CustomerEntity toEntity(Customer customer) {
        return new CustomerEntity(customer.getUsername().getValue(),
                customer.getPassword().getValue(),
                customer.getNickname().getValue(),
                customer.getAge());
    }
}
