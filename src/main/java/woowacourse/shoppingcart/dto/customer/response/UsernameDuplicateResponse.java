package woowacourse.shoppingcart.dto.customer.response;

public class UsernameDuplicateResponse {

    private final String username;
    private final boolean duplicated;

    public UsernameDuplicateResponse() {
        this(null, true);
    }

    public UsernameDuplicateResponse(String username, boolean duplicated) {
        this.username = username;
        this.duplicated = duplicated;
    }

    public String getUsername() {
        return username;
    }

    public boolean isDuplicated() {
        return duplicated;
    }
}
