package woowacourse.shoppingcart.dto;

public class ExistCartErrorResponse extends ErrorResponse {
    private final boolean redirect = true;

    public ExistCartErrorResponse(String message) {
        super(message);
    }

    public boolean isRedirect() {
        return redirect;
    }
}
