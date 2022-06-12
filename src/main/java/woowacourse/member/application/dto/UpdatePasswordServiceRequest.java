package woowacourse.member.application.dto;

public class UpdatePasswordServiceRequest {

    private final long memberId;
    private final String oldPassword;
    private final String newPassword;

    public UpdatePasswordServiceRequest(long memberId, String oldPassword, String newPassword) {
        this.memberId = memberId;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public long getMemberId() {
        return memberId;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
}
