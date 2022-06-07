package woowacourse.shoppingcart.dto.response;

public class EmailDuplicationResponse {
    private boolean isDuplicated;

    public EmailDuplicationResponse(boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
    }

    public EmailDuplicationResponse() {
    }

    public boolean getIsDuplicated() {
        return isDuplicated;
    }

    @Override
    public String toString() {
        return "EmailDuplicationResponse{" +
                "isDuplicated=" + isDuplicated +
                '}';
    }
}
