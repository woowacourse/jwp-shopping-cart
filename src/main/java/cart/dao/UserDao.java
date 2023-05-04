package cart.dao;

import cart.entity.UserEntity;

import java.util.List;

public interface UserDao {
    int insert(UserEntity user);

    List<UserEntity> selectAll();

    int update(UserEntity user);

    int delete(int userId);
}
