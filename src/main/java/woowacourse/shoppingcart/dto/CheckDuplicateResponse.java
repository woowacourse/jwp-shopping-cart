package woowacourse.shoppingcart.dto;

public class CheckDuplicateResponse {

    private final boolean isDuplicate;

    public CheckDuplicateResponse(boolean isDuplicate) {
        this.isDuplicate = isDuplicate;
    }

    public boolean getIsDuplicate() {
        return isDuplicate;
    }
}
