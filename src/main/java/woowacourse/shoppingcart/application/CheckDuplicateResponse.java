package woowacourse.shoppingcart.application;

public class CheckDuplicateResponse {

    private final boolean result;

    public CheckDuplicateResponse(boolean result) {
        this.result = result;
    }

    public boolean isResult() {
        return result;
    }
}
