package cart.dto;

public class MemberDto {

    private String email;
    private String password;

    public MemberDto() {
    }

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
