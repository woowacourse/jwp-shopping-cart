package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;

public class UpdatePasswordRequest {

    @NotBlank
    private String password;
    @NotBlank
    private String newPassword;

    public UpdatePasswordRequest() {
    }

    public UpdatePasswordRequest(String password, String newPassword) {
        this.password = password;
        this.newPassword = newPassword;
    }

    public String getPassword() {
        return password;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
