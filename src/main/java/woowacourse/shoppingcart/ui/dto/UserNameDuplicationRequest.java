package woowacourse.shoppingcart.ui.dto;

public class UserNameDuplicationRequest {

    private final String username;

    public UserNameDuplicationRequest() {
        this(null);
    }

    public UserNameDuplicationRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
