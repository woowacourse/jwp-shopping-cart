package woowacourse.shoppingcart.dto;

public class CheckDuplicateRequest {

    private String userName;

    public CheckDuplicateRequest() {}

    public CheckDuplicateRequest(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
