package woowacourse.shoppingcart.infra.dao;

import java.util.Optional;
import woowacourse.shoppingcart.infra.dao.entity.CustomerEntity;

public interface CustomerDao {
    long save(CustomerEntity customerEntity);

    Optional<CustomerEntity> findById(long id);

    Optional<CustomerEntity> findByEmail(String email);

    Optional<CustomerEntity> findByName(String name);

    void update(CustomerEntity customerEntity);

    void deleteById(long id);
}
