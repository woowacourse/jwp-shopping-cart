package woowacourse.shoppingcart.dao;

import woowacourse.shoppingcart.entity.AddressEntity;

public interface AddressDao {
    void save(AddressEntity addressEntity);

    AddressEntity findById(int id);

    void update(AddressEntity addressEntity);

    void delete(int customerId);
}
