package woowacourse.shoppingcart.dto.response;

public class AlreadyExistCartItemResponse {

    private boolean redirect;

    public AlreadyExistCartItemResponse() {
    }

    public AlreadyExistCartItemResponse(boolean redirect) {
        this.redirect = redirect;
    }

    public boolean isRedirect() {
        return redirect;
    }

    public void setRedirect(boolean redirect) {
        this.redirect = redirect;
    }
}
