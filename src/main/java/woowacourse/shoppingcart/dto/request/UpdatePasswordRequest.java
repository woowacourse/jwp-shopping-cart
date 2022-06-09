package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UpdatePasswordRequest {

    @NotBlank
    @Size(min=8, max=20)
    private String oldPassword;
    @NotBlank
    @Size(min=8, max=20)
    private String newPassword;

    public UpdatePasswordRequest() {
    }

    public UpdatePasswordRequest(String oldPassword, String newPassword) {
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
