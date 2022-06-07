package woowacourse.shoppingcart.dto.response;

public class UserNameDuplicationResponse {
    private boolean isUnique;

    private UserNameDuplicationResponse() {
    }

    public UserNameDuplicationResponse(boolean isUnique) {
        this.isUnique = isUnique;
    }

    public boolean getIsUnique() {
        return isUnique;
    }
}
