package cart.dto.member;

public class MemberLoginRequestDto {

    private String email;
    private String password;

    private MemberLoginRequestDto() {

    }

    private MemberLoginRequestDto(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    public static MemberLoginRequestDto from(final String email, final String password) {
        return new MemberLoginRequestDto(email, password);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
