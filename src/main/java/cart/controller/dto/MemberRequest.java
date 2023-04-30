package cart.controller.dto;

import javax.validation.constraints.NotNull;

public class MemberRequest {

    @NotNull
    private final String email;
    @NotNull
    private final String password;

    public MemberRequest(String email, String password) {
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
