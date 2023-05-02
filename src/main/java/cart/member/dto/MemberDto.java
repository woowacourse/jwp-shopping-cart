package cart.member.dto;

public class MemberDto {

    private final String email;
    private final String password;

    public MemberDto(final String email, final String password) {
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
