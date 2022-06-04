package woowacourse.shoppingcart.dto;

public class CheckDuplicateRequest {

    private final String userName;

    public CheckDuplicateRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
