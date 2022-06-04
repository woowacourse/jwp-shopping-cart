package woowacourse.shoppingcart.dao;

import woowacourse.shoppingcart.domain.customer.Customer;
import woowacourse.shoppingcart.entity.CustomerEntity;

public interface CustomerDao {
    int save(Customer customer);

    CustomerEntity findById(int id);

    CustomerEntity findByEmail(String email);

    void update(int id, Customer customer);

    void delete(int id);

    boolean hasEmail(String email);
}
