package woowacourse.shoppingcart.dto;

public class CheckDuplicationRequest {

    private String userName;

    public CheckDuplicationRequest() {
    }

    public CheckDuplicationRequest(final String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
