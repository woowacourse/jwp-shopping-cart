package cart.dto.application;

public class AccountDto {
    private final String username;
    private final String password;

    public AccountDto(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
