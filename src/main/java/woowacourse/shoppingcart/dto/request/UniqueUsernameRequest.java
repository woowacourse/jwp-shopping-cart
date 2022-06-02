package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotNull;

public class UniqueUsernameRequest {

    @NotNull
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
