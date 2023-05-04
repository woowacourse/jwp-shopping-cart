package cart.dao;

import cart.dto.AuthInfo;
import cart.entity.UserEntity;

import java.util.List;

public interface UserDao {
    int insert(UserEntity user);

    List<UserEntity> selectAll();

    int selectByAuth(AuthInfo authInfo);

    int update(UserEntity user);

    int delete(int userId);
}
