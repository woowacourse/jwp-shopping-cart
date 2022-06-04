package woowacourse.shoppingcart.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UniqueUsernameRequest {

    @NotBlank
    @Size(min=4, max=20)
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
