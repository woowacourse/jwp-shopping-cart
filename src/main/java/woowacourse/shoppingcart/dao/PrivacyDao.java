package woowacourse.shoppingcart.dao;

import woowacourse.shoppingcart.entity.PrivacyEntity;

public interface PrivacyDao {
    void save(PrivacyEntity privacyEntity);

    PrivacyEntity findById(int id);

    void update(PrivacyEntity privacyEntity);

    void delete(int id);
}
