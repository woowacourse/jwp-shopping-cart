package woowacourse.shoppingcart.dto.customer.response;

public class EmailDuplicateResponse {

    private final String email;
    private final boolean duplicated;

    public EmailDuplicateResponse() {
        this(null, true);
    }

    public EmailDuplicateResponse(String email, boolean duplicated) {
        this.email = email;
        this.duplicated = duplicated;
    }

    public String getEmail() {
        return email;
    }

    public boolean isDuplicated() {
        return duplicated;
    }
}
