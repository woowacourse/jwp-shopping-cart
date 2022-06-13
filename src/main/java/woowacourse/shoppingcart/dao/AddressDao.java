package woowacourse.shoppingcart.dao;

import java.util.Optional;
import woowacourse.shoppingcart.entity.AddressEntity;

public interface AddressDao {
    void save(long customerId, AddressEntity addressEntity);

    Optional<AddressEntity> findById(long customerId);

    void update(long customerId, AddressEntity addressEntity);

    void delete(long customerId);
}
