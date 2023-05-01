package cart.controller.dto;

import javax.validation.constraints.NotNull;

public class MemberRequest {

    @NotNull
    private String email;
    @NotNull
    private String password;

    public MemberRequest() {
    }

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
