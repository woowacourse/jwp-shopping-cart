package woowacourse.shoppingcart.application.dto;

public class UserNameDuplicationResponse {

    private final String username;
    private final Boolean duplicated;

    public UserNameDuplicationResponse() {
        this(null, null);
    }

    public UserNameDuplicationResponse(String username, Boolean duplicated) {
        this.username = username;
        this.duplicated = duplicated;
    }

    public String getUsername() {
        return username;
    }

    public Boolean getDuplicated() {
        return duplicated;
    }
}
