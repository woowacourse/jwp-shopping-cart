package woowacourse.auth.dto;

public class CheckResponse {

    private boolean success;

    public CheckResponse() {
    }

    public CheckResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
