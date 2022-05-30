package woowacourse.auth.dao;

import woowacourse.auth.domain.user.Email;
import woowacourse.auth.domain.user.Id;
import woowacourse.auth.entity.UserEntity;

public interface UserDao {
    int save(UserEntity userEntity);

    UserEntity findById(int id);

    UserEntity findByEmail(Email email);

    void update(UserEntity userEntity);

    void delete(Id id);
}
