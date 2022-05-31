package woowacourse.shoppingcart.dto.request;

public class UniqueUsernameRequest {

    private String username;

    public UniqueUsernameRequest() {
    }

    public UniqueUsernameRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
