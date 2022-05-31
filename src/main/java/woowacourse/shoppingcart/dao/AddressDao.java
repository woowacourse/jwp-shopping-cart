package woowacourse.shoppingcart.dao;

import woowacourse.shoppingcart.entity.AddressEntity;

public interface AddressDao {
    void save(int customerId, AddressEntity addressEntity);

    AddressEntity findById(int id);

    void update(int customerId, AddressEntity addressEntity);

    void delete(int customerId);
}
