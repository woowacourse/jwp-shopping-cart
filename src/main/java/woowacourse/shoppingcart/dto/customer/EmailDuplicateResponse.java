package woowacourse.shoppingcart.dto.customer;

public class EmailDuplicateResponse {

    private final String email;
    private final boolean duplicate;

    public EmailDuplicateResponse() {
        this(null, true);
    }

    public EmailDuplicateResponse(String email, boolean duplicate) {
        this.email = email;
        this.duplicate = duplicate;
    }

    public String getUsername() {
        return email;
    }

    public boolean isDuplicate() {
        return duplicate;
    }
}
