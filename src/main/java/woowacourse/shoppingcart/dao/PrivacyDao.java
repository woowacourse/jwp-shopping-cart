package woowacourse.shoppingcart.dao;

import woowacourse.shoppingcart.domain.customer.privacy.Privacy;
import woowacourse.shoppingcart.entity.PrivacyEntity;

public interface PrivacyDao {
    void save(int customerId, Privacy privacy);

    PrivacyEntity findById(int customerId);

    void update(int customerId, Privacy privacy);

    void delete(int customerId);
}
