package cart.dto.user;

import org.springframework.stereotype.Component;

import cart.dao.entity.User;

@Component
public class UserMapper {

    public UserResponse toResponse(User user) {
        return new UserResponse(user.getEmail(), user.getPassword());
    }

    public User toEntity(UserRequest userRequest) {
        return new User(userRequest.getEmail(), userRequest.getPassword());
    }
}
