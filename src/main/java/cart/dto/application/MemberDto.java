package cart.dto.application;

public class MemberDto {
    private final String username;
    private final String password;

    public MemberDto(final String username, final String password) {
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
