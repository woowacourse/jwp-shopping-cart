package woowacourse.shoppingcart.application.dto;

public class EmailDuplicationResponse {

    private boolean isDuplicated;

    public EmailDuplicationResponse() {
    }

    public EmailDuplicationResponse(boolean isDuplicated) {
        this.isDuplicated = isDuplicated;
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
