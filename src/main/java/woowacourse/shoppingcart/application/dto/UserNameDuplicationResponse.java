package woowacourse.shoppingcart.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserNameDuplicationResponse {

    private final String username;

    @JsonProperty("duplicated")
    private final Boolean isDuplicated;

    private UserNameDuplicationResponse() {
        this(null, null);
    }

    public UserNameDuplicationResponse(String username, Boolean isDuplicated) {
        this.username = username;
        this.isDuplicated = isDuplicated;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getDuplicated() {
        return isDuplicated;
    }
}
