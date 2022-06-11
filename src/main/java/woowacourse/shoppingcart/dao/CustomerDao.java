package woowacourse.shoppingcart.dao;

import woowacourse.shoppingcart.entity.CustomerEntity;

public interface CustomerDao {
    int save(CustomerEntity customerEntity);

    CustomerEntity findById(long id);

    CustomerEntity findByEmail(String email);

    void update(long id, CustomerEntity customerEntity);

    void delete(long id);

    boolean existsById(long id);

    boolean existsByEmail(String email);
}
