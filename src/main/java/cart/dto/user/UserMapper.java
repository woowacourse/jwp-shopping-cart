package cart.dto.user;

import cart.dao.entity.User;

public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getEmail(), user.getPassword());
    }
}
