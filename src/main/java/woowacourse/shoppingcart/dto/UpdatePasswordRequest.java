package woowacourse.shoppingcart.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdatePasswordRequest {

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{6,}$")
    @Size(min = 6)
    private String password;

    @NotBlank
    @Pattern(regexp = "^[a-zA-Z\\\\d`~!@#$%^&*()-_=+]{6,}$")
    @Size(min = 6)
    private String newPassword;

    private UpdatePasswordRequest() {
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
