package woowacourse.shoppingcart.dao;

import woowacourse.shoppingcart.entity.PrivacyEntity;

public interface PrivacyDao {
    void save(int customerId, PrivacyEntity privacyEntity);

    PrivacyEntity findById(int id);

    void update(int customerId, PrivacyEntity privacyEntity);

    void delete(int id);
}
