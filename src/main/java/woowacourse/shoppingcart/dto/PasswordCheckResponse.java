package woowacourse.shoppingcart.dto;

public class PasswordCheckResponse {

    private boolean success;

    public PasswordCheckResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
