package woowacourse.shoppingcart.dto.request;

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
