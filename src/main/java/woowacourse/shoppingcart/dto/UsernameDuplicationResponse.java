package woowacourse.shoppingcart.dto;

public class UsernameDuplicationResponse {
    private boolean isUnique;

    private UsernameDuplicationResponse() {
    }

    public UsernameDuplicationResponse(boolean isUnique) {
        this.isUnique = isUnique;
    }

    public boolean getIsUnique() {
        return isUnique;
    }
}
