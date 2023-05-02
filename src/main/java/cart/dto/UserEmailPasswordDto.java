package cart.dto;

import cart.domain.User;

public class UserEmailPasswordDto {
    private String email;
    private String password;

    public UserEmailPasswordDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public static UserEmailPasswordDto from(User user) {
        return new UserEmailPasswordDto(user.getEmail(), user.getPassword());
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
