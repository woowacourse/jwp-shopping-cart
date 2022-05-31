package woowacourse.auth.dao;

import woowacourse.auth.entity.AddressEntity;

public interface AddressDao {
    void save(int customerId, AddressEntity addressEntity);

    AddressEntity findById(int id);

    void update(int customerId, AddressEntity addressEntity);

    void delete(int customerId);
}
