package woowacourse.shoppingcart.dto;

public class UsernameDuplicationRequest {
    private String username;

    private UsernameDuplicationRequest() {
    }

    public UsernameDuplicationRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
