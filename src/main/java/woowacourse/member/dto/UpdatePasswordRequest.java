package woowacourse.member.dto;

public class UpdatePasswordRequest {

    private String oldPassword;
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

    public String getNewPassword() {
        return newPassword;
    }
}
