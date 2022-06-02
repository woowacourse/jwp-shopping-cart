package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotNull;

public class UpdatePasswordRequest {

    @NotNull(message = "기존 비밀번호 입력 필요")
    private String oldPassword;
    @NotNull(message = "새로운 비밀번호 입력 필요")
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
