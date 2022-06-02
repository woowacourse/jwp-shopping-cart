package woowacourse.auth.dto.response;

public class PasswordCheckResponse {

    private boolean success;

    private PasswordCheckResponse() {
    }

    public PasswordCheckResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
