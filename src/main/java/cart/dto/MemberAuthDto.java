package cart.dto;

public final class MemberAuthDto {

    private String email;
    private String password;

    public MemberAuthDto(String email, String password) {
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
