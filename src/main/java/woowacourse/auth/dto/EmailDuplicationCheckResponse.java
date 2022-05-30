package woowacourse.auth.dto;

public class EmailDuplicationCheckResponse {

    private boolean success;

    public EmailDuplicationCheckResponse() {
    }

    public EmailDuplicationCheckResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
