package woowacourse.auth.dao;

import woowacourse.auth.domain.user.Email;
import woowacourse.auth.domain.user.Id;
import woowacourse.auth.domain.user.User;

public interface UserDao {
    Id save(User user);

    User findById(Id id);

    User findByEmail(Email email);

    void update(User user);

    void delete(Id id);
}
