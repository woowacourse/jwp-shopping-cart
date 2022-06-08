package woowacourse.member.dto.response;

public class PasswordCheckResponse {

    private boolean success;

    public PasswordCheckResponse() {
    }

    public PasswordCheckResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
