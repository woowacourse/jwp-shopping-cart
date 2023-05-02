package cart.dto;

import cart.entity.User;

public final class UserDto {
    private final Long id;
    private final String email;
    private final String password;

    public UserDto(final Long id, final String email, final String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static UserDto from(User user) {
        return new UserDto(user.getId(), user.getEmail(), user.getPassword());
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
