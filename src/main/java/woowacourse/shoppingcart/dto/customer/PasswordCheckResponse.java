package woowacourse.shoppingcart.dto.customer;

public class PasswordCheckResponse {

    private final boolean success;

    public PasswordCheckResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }
}
