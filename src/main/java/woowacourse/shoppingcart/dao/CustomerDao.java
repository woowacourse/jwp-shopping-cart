package woowacourse.shoppingcart.dao;

import woowacourse.shoppingcart.entity.CustomerEntity;

public interface CustomerDao {
    int save(CustomerEntity customerEntity);

    CustomerEntity findById(int id);

    CustomerEntity findByEmail(String email);

    void update(int id, CustomerEntity customerEntity);

    void delete(int id);

    boolean hasEmail(String email);
}
