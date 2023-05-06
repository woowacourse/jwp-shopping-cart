package cart.service.dto;

public class UserAuthDto {

    private String email;
    private String password;

    public UserAuthDto(String email, String password) {
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
