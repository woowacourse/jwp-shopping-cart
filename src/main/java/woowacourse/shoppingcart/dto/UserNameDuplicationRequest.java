package woowacourse.shoppingcart.dto;

public class UserNameDuplicationRequest {
    private String username;

    private UserNameDuplicationRequest() {
    }

    public UserNameDuplicationRequest(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
