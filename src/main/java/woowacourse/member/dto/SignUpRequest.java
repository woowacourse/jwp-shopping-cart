package woowacourse.member.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

public class SignUpRequest {

    @NotNull
    @Email
    private final String email;
    @NotNull
    private final String name;
    @NotNull
    private final String password;

    public SignUpRequest(String email, String name, String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }
}
