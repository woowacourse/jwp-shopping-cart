package woowacourse.shoppingcart.repository;

import java.util.Optional;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.dao.CustomerDao;
import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.domain.customer.vo.PhoneNumber;
import woowacourse.shoppingcart.entity.CustomerEntity;

@Repository
public class CustomerRepository {

    private final CustomerDao customerDao;

    public CustomerRepository(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void save(Customer customer) {
        customerDao.save(CustomerEntity.from(customer));
    }

    public Optional<Customer> findByAccount(String account) {
        return customerDao.findByAccount(account)
                .map(this::toCustomer);

    }

    public Optional<Customer> findById(Long customerId) {
        return customerDao.findById(customerId)
                .map(this::toCustomer);
    }

    private Customer toCustomer(CustomerEntity it) {
        return new Customer(
                it.getId(),
                it.getAccount(),
                it.getNickname(),
                it.getPassword(),
                it.getAddress(),
                new PhoneNumber(it.getPhoneNumber()
                ));
    }
}
