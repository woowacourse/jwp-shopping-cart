package woowacourse.shoppingcart.dao;

import java.util.Optional;
import woowacourse.shoppingcart.entity.CustomerEntity;

public interface CustomerDao {
    int save(CustomerEntity customerEntity);

    Optional<CustomerEntity> findById(long id);

    Optional<CustomerEntity> findByEmail(String email);

    void update(long id, CustomerEntity customerEntity);

    void delete(long id);

    boolean existsById(long id);

    boolean existsByEmail(String email);
}
