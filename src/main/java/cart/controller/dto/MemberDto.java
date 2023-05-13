package cart.controller.dto;

public class MemberDto {

    private String email;
    private String password;
    private String name;

    private MemberDto() {
    }

    public MemberDto(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
