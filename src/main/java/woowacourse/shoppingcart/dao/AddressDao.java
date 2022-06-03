package woowacourse.shoppingcart.dao;

import woowacourse.shoppingcart.domain.customer.address.FullAddress;
import woowacourse.shoppingcart.entity.AddressEntity;

public interface AddressDao {
    void save(int customerId, FullAddress fullAddress);

    AddressEntity findById(int customerId);

    void update(int customerId, FullAddress fullAddress);

    void delete(int customerId);
}
