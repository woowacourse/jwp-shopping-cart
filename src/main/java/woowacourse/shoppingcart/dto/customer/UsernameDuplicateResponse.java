package woowacourse.shoppingcart.dto.customer;

public class UsernameDuplicateResponse {

    private final String username;
    private final boolean duplicate;

    public UsernameDuplicateResponse() {
        this(null, true);
    }

    public UsernameDuplicateResponse(String username, boolean duplicate) {
        this.username = username;
        this.duplicate = duplicate;
    }

    public String getUsername() {
        return username;
    }

    public boolean isDuplicate() {
        return duplicate;
    }
}
