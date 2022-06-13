package woowacourse.shoppingcart.dto.response;

public class RedirectErrorResponse extends ErrorResponse {
    private boolean redirect;

    public RedirectErrorResponse(String message) {
        super(message);
        this.redirect = true;
    }

    public boolean isRedirect() {
        return redirect;
    }
}
