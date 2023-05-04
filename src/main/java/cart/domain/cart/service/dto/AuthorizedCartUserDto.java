package cart.domain.cart.service.dto;

public class AuthorizedCartUserDto {
    private final String email;
    private final String password;

    public AuthorizedCartUserDto(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
