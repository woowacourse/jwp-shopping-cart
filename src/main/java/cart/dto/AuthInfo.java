package cart.dto;

import javax.validation.constraints.NotBlank;

public class AuthInfo {

    @NotBlank(message = "email은 공백일 수 없습니다.")
    private final String email;
    @NotBlank(message = "password는 공백일 수 없습니다.")
    private final String password;

    public AuthInfo(String email, String password) {
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
