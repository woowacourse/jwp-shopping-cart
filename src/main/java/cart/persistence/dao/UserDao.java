package cart.persistence.dao;

import cart.persistence.entity.UserEntity;

public interface UserDao extends Dao<UserEntity> {
    Long findUserIdByEmail(final String email);
}
