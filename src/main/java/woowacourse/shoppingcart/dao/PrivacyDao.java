package woowacourse.shoppingcart.dao;

import java.util.Optional;
import woowacourse.shoppingcart.entity.PrivacyEntity;

public interface PrivacyDao {
    void save(long customerId, PrivacyEntity privacyEntity);

    Optional<PrivacyEntity> findById(long customerId);

    void update(long customerId, PrivacyEntity privacyEntity);

    void delete(long customerId);
}
