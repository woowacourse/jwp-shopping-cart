package woowacourse.auth.dao;

import woowacourse.auth.entity.PrivacyEntity;

public interface PrivacyDao {
    void save(int customerId, PrivacyEntity privacyEntity);

    PrivacyEntity findById(int id);

    void update(PrivacyEntity privacyEntity);

    void delete(int id);
}
