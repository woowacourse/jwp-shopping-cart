package woowacourse.shoppingcart.infra;

import java.util.Optional;
import java.util.function.Function;
import org.springframework.stereotype.Repository;
import woowacourse.shoppingcart.domain.Customer;
import woowacourse.shoppingcart.infra.dao.CustomerDao;
import woowacourse.shoppingcart.infra.dao.entity.CustomerEntity;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private final CustomerDao customerDao;

    public JdbcCustomerRepository(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public Customer save(Customer customer) {
        final CustomerEntity customerEntity = toEntity(customer);

        if (customer.isNew()) {
            final long id = customerDao.save(customerEntity);
            return new Customer(id, customerEntity.getEmail(), customer.getName(), customer.getPassword());
        }

        customerDao.update(customerEntity);
        return customer;
    }

    private CustomerEntity toEntity(Customer customer) {
        return new CustomerEntity(customer.getId(), customer.getEmail(),
                customer.getName(), customer.getPassword());
    }

    @Override
    public Optional<Customer> findById(long id) {
        return customerDao.findById(id)
                .map(toOptionalCustomer());
    }

    private Function<CustomerEntity, Customer> toOptionalCustomer() {
        return entity -> new Customer(entity.getId(), entity.getEmail(), entity.getName(), entity.getPassword());
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerDao.findByEmail(email)
                .map(toOptionalCustomer());
    }

    @Override
    public Optional<Customer> findByName(String name) {
        return customerDao.findByName(name)
                .map(toOptionalCustomer());
    }

    @Override
    public void deleteById(long id) {
        customerDao.deleteById(id);
    }
}
