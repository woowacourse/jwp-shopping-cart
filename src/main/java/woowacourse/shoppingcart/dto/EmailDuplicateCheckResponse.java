package woowacourse.shoppingcart.dto;

public class EmailDuplicateCheckResponse {

    private final boolean success;

    public EmailDuplicateCheckResponse(boolean success) {
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }
}
