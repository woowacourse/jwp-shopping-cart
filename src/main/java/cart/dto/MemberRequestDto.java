package cart.dto;

public class MemberRequestDto {
    private String email;
    private String password;

    public MemberRequestDto() {
    }

    public MemberRequestDto(String email, String password) {
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
