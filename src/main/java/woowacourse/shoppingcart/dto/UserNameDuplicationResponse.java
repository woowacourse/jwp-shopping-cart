package woowacourse.shoppingcart.dto;

public class UserNameDuplicationResponse {
    private boolean unique;

    private UserNameDuplicationResponse() {
    }

    public UserNameDuplicationResponse(boolean unique) {
        this.unique = unique;
    }

    public boolean isUnique() {
        return unique;
    }
}
