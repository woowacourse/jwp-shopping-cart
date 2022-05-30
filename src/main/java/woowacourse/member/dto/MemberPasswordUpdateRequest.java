package woowacourse.member.dto;

public class MemberPasswordUpdateRequest {

    private String oldPassword;
    private String newPassword;

    private MemberPasswordUpdateRequest() {
    }

    public MemberPasswordUpdateRequest(String oldPassword, String newPassword) {
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
