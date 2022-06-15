package woowacourse.shoppingcart.dto.customer;

public class PasswordCheckResponse {

    private String success;

    private PasswordCheckResponse() {
    }

    public PasswordCheckResponse(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }
}
